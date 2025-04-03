package com.shubhamtripz.iplbubble.presentation.matches

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.shubhamtripz.iplbubble.presentation.matches.iplfixtures.IplFixturesScreen
import com.shubhamtripz.iplbubble.presentation.matches.pointstable.PointsTableScreen
import com.shubhamtripz.iplbubble.presentation.matches.stats.StatsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchesScreen(navController: NavController) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        val tabTitles = listOf("IPL Fixtures", "Points Table", "Stats")
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFF1A1A2E),
            contentColor = Color.White
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> IplFixturesScreen()
                1 -> PointsTableScreen()
                2 -> StatsScreen()
            }
        }
    }
}
