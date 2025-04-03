package com.shubhamtripz.iplbubble.data.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import com.shubhamtripz.iplbubble.domain.models.MatchData
import org.json.JSONObject
import java.util.UUID

class MatchRepository(private val webView: WebView) {

    private val TAG = "MatchRepository"

    init {
        setupWebView()
    }

    // this web view is not visible to user, its run on background , we use to scrape data from web view loaded data
    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true

            cacheMode = WebSettings.LOAD_NO_CACHE
            domStorageEnabled = true
        }

        webView.clearCache(true)
        webView.clearHistory()

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d(TAG, "Page loading progress: $newProgress%")
            }
        }
    }

    fun fetchLiveScore(callback: (MatchData?) -> Unit) {
        webView.clearCache(true)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "Page loaded: $url")

                extractLiveScore(callback)
            }
        }

        // Add a random parameter to the URL to prevent caching (as we facing issue in updating live score)
        val uniqueUrl = "https://indianexpress.com/section/sports/cricket/live-score/?nocache=${UUID.randomUUID()}"
        webView.loadUrl(uniqueUrl)
    }

    // Logic to Scrape: we are hitting a url which shows live ipl score we are just scrapping their html values od score and updating it in our UI
    private fun extractLiveScore(callback: (MatchData?) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            webView.evaluateJavascript(
                """
                (function() {
                    var matchDiv = document.querySelector('.live_match');
                    if (!matchDiv) {
                        console.log('No live match div found');
                        return null;
                    }
                    console.log('Found live match div');

                    function getText(selector) {
                        let el = matchDiv.querySelector(selector);
                        return el ? el.innerText.trim() : 'N/A';
                    }

                    function getAttr(selector, attr) {
                        let el = matchDiv.querySelector(selector);
                        return el ? el.getAttribute(attr) : '';
                    }

                    var result = {
                        title: getText('.match_num'),
                        matchDate: getText('.match_date_time'),
                        venue: getText('.match_location'),
                        teamName: getText('.team_one .team_one_name a'),
                        teamScore: getText('.team_one .runs'),
                        teamOver: getText('.team_one .overs'),
                        teamImage: getAttr('.team_one .team_flag img', 'src'),
                        teamNameOponent: getText('.team_two .team_two_name a'),
                        teamScoreOponent: getText('.team_two .runs'),
                        teamOverOponent: getText('.team_two .overs'),
                        teamImageOponent: getAttr('.team_two .team_flag img', 'src'),
                        matchHiLight: getText('.match_summery')
                    };
                    
                    console.log('Extracted data:', JSON.stringify(result));
                    return JSON.stringify(result);
                })();
                """.trimIndent()
            ) { result ->
                Log.d(TAG, "JavaScript result: $result")
                parseResult(result, callback)
            }
        }, 2000) // Increased delay as, the website some show cached data and then instatntly update to new data
    }

    private fun parseResult(result: String?, callback: (MatchData?) -> Unit) {
        if (result.isNullOrBlank() || result == "null") {
            Log.e(TAG, "No data received from WebView.")
            callback(null)
            return
        }
        try {

            val jsonString = result.trim('"').replace("\\\"", "\"").replace("\\\\", "\\")
            val json = JSONObject(jsonString)

            val matchData = MatchData(
                title = json.optString("title", "N/A"),
                matchDate = json.optString("matchDate", "N/A"),
                teamName = json.optString("teamName", "N/A"),
                teamScore = json.optString("teamScore", ""),
                teamOver = json.optString("teamOver", ""),
                teamImage = json.optString("teamImage", ""),
                teamNameOponent = json.optString("teamNameOponent", ""),
                teamScoreOponent = json.optString("teamScoreOponent", ""),
                teamOverOponent = json.optString("teamOverOponent", ""),
                teamImageOponent = json.optString("teamImageOponent", ""),
                matchHiLight = json.optString("matchHiLight", "No Highlights"),
                venue = json.optString("venue", "N/A")
            )
            Log.d(TAG, "Successfully parsed match data: ${matchData.teamName} vs ${matchData.teamNameOponent}")
            callback(matchData)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing result: ${e.message}")
            Log.e(TAG, "Raw result: $result")
            callback(null)
        }
    }
}