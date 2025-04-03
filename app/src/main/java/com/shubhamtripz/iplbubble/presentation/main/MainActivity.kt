package com.shubhamtripz.iplbubble.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.shubhamtripz.iplbubble.presentation.auth.AuthActivity
import com.shubhamtripz.iplbubble.presentation.fantasy.FantasyScreen
import com.shubhamtripz.iplbubble.presentation.home.HomeScreen
import com.shubhamtripz.iplbubble.presentation.home.livescore.MatchViewModel
import com.shubhamtripz.iplbubble.presentation.matches.MatchesScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        val sharedPreferences = getSharedPreferences("IPL_Bubble_Prefs", Context.MODE_PRIVATE)
        val skippedLogin = sharedPreferences.getBoolean("skipped_login", false)

        if (auth.currentUser == null && !skippedLogin) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        val webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }

        setContent {
            MainNavigation(webView)
        }
    }
}

@Composable
fun MainNavigation(webView: WebView) {
    val navController = rememberNavController()
    val viewModel: MatchViewModel = getViewModel { parametersOf(webView) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(webView, navController) }
            composable(Screen.Matches.route) { MatchesScreen(navController) }
            composable(Screen.Fantasy.route) { FantasyScreen() }
        }
    }
}
