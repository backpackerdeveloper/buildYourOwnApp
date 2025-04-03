package com.shubhamtripz.iplbubble.presentation.matches.pointstable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.shubhamtripz.iplbubble.presentation.matches.pointstable.components.PointsTableHeader
import com.shubhamtripz.iplbubble.presentation.matches.pointstable.components.PointsTableItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun PointsTableScreen(viewModel: PointsTableViewModel = koinViewModel()) {
    val pointsTable by viewModel.pointsTable.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        if (pointsTable.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            LazyColumn {
                item {
                    PointsTableHeader()
                }
                items(pointsTable) { team ->
                    PointsTableItem(team)
                }
            }
        }
    }
}