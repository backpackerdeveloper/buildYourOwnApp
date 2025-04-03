package com.shubhamtripz.iplbubble.presentation.matches.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.shubhamtripz.iplbubble.presentation.matches.stats.components.PlayerStatsCard
import com.shubhamtripz.iplbubble.presentation.matches.stats.components.StatsShimmerEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatsScreen(viewModel: StatsViewModel = koinViewModel()) {
    val stats by viewModel.stats.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (stats == null) {
            Column(modifier = Modifier.fillMaxSize()) {
                repeat(10) { StatsShimmerEffect() }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(stats!!) { player ->
                    PlayerStatsCard(player)
                }
            }
        }
    }
}