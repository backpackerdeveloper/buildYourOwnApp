package com.shubhamtripz.iplbubble.presentation.fantasy

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import com.shubhamtripz.iplbubble.presentation.fantasy.livebets.LiveBetsScreen
import com.shubhamtripz.iplbubble.presentation.fantasy.mybets.MyBetsScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FantasyScreen() {
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFF1A1A2E),
            contentColor = Color.White
        ) {
            listOf("Live Bets", "My Bets").forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState
        ) { page ->
            when (page) {
                0 -> LiveBetsScreen()
                1 -> MyBetsScreen()
            }
        }
    }
}
