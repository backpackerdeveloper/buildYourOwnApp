package com.shubhamtripz.iplbubble.domain.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.shubhamtripz.iplbubble.R

class BubbleService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var bubbleView: View
    private lateinit var layoutParams: WindowManager.LayoutParams

    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0f
    private var initialTouchY: Float = 0f

    private var teamName: String = " "
    private var teamScore: String = ""
    private var teamOver: String = ""
    private var teamNameOponent: String = ""
    private var teamScoreOponent: String = ""
    private var teamOverOponent: String = ""

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        Log.d("BubbleService", "Overlay Permission: ${Settings.canDrawOverlays(this)}")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            teamName = it.getStringExtra("teamName") ?: ""
            teamScore = it.getStringExtra("teamScore") ?: ""
            teamOver = it.getStringExtra("teamOver") ?: ""
            teamNameOponent = it.getStringExtra("teamNameOponent") ?: ""
            teamScoreOponent = it.getStringExtra("teamScoreOponent") ?: ""
            teamOverOponent = it.getStringExtra("teamOverOponent") ?: ""
        }

        if (!::bubbleView.isInitialized) {
            initializeBubble()
        } else {
            updateBubbleData()
        }

        return START_STICKY
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeBubble() {
        Log.d("BubbleService", "Adding bubble to WindowManager")
        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.x = 0
        layoutParams.y = 100

        bubbleView = LayoutInflater.from(this).inflate(R.layout.layout_bubble, null)

        bubbleView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = layoutParams.x
                    initialY = layoutParams.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    layoutParams.x = initialX + (event.rawX - initialTouchX).toInt()
                    layoutParams.y = initialY + (event.rawY - initialTouchY).toInt()
                    windowManager.updateViewLayout(bubbleView, layoutParams)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY

                    // Detect a tap here
                    if (Math.abs(deltaX) < 10 && Math.abs(deltaY) < 10) {
                        launchApp()
                    }
                    true
                }
                else -> false
            }
        }

        updateBubbleData()

        try {
            windowManager.addView(bubbleView, layoutParams)
        } catch (e: Exception) {
            Log.e("BubbleService", "Failed to add bubble: ${e.message}")
        }
    }


    private fun updateBubbleData() {
        val tvTeam1 = bubbleView.findViewById<TextView>(R.id.tvTeam1)
        val tvScore1 = bubbleView.findViewById<TextView>(R.id.tvScore1)
        val tvTeam2 = bubbleView.findViewById<TextView>(R.id.tvTeam2)
        val tvScore2 = bubbleView.findViewById<TextView>(R.id.tvScore2)

        tvTeam1.text = teamName
        tvScore1.text = "$teamScore $teamOver"

        tvTeam2.text = teamNameOponent
        tvScore2.text = "$teamScoreOponent $teamOverOponent"
    }

    private fun launchApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (::bubbleView.isInitialized && bubbleView.isAttachedToWindow) {
            windowManager.removeView(bubbleView)
        }
    }
}