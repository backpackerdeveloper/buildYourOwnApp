package com.shubhamtripz.iplbubble.presentation.matches.pointstable.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubhamtripz.iplbubble.domain.models.PointsTable

@Composable
fun PointsTableItem(team: PointsTable) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = team.Teams, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black, modifier = Modifier.weight(2f))
            Text(text = "${team.Played}", fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
            Text(text = "${team.Win}", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Green, modifier = Modifier.weight(1f))
            Text(text = "${team.Loss}", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Red, modifier = Modifier.weight(1f))
            Text(text = "${team.PTS}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Blue, modifier = Modifier.weight(1f))
            Text(text = "${team.NRR}", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.weight(1.5f))
        }

        Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 8.dp))
    }
}
