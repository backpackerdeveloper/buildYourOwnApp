package com.shubhamtripz.iplbubble.presentation.topstories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class TopStoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUrl = intent.getStringExtra("tp_imageUrl") ?: ""
        val title = intent.getStringExtra("tp_title") ?: ""
        val description = intent.getStringExtra("tp_post_description") ?: ""

        setContent {
            TopStoriesScreen(imageUrl, title, description)
        }
    }
}