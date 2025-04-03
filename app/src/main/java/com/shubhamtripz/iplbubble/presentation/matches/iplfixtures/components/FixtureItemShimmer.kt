package com.shubhamtripz.iplbubble.presentation.matches.iplfixtures.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun FixtureItemShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .shimmer(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            ShimmerCircle()
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerBar(width = 40.dp, height = 12.dp)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1.5f)
        ) {
            ShimmerBar(width = 80.dp, height = 12.dp)
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerBar(width = 100.dp, height = 14.dp)
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerBar(width = 80.dp, height = 12.dp)
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerBar(width = 120.dp, height = 12.dp)
        }

        // 🔹 Team B (Shimmer Placeholder)
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            ShimmerCircle()
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerBar(width = 40.dp, height = 12.dp)
        }
    }
}

@Composable
fun ShimmerCircle() {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.3f)),
        modifier = Modifier.size(50.dp)
    ) {}
}

@Composable
fun ShimmerBar(width: Dp, height: Dp) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.3f)),
        modifier = Modifier
            .width(width)
            .height(height)
    ) {}
}