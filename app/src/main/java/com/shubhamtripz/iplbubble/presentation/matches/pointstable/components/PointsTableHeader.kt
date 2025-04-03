package com.shubhamtripz.iplbubble.presentation.matches.pointstable.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PointsTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Team", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(2f))
        Text(text = "P", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(1f))
        Text(text = "W", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Green, modifier = Modifier.weight(1f))
        Text(text = "L", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Red, modifier = Modifier.weight(1f))
        Text(text = "PTS", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Blue, modifier = Modifier.weight(1f))
        Text(text = "NRR", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray, modifier = Modifier.weight(1.5f))
    }
}
