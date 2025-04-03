package com.shubhamtripz.iplbubble.presentation.matches.iplfixtures.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubhamtripz.iplbubble.domain.models.IplFixture

@Composable
fun FixtureItem(fixture: IplFixture) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Team A
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            TeamLogo(fixture.TeamAlogo)
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = fixture.TeamA,
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }

        // Match Details
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1.5f)
        ) {
            Text(text = fixture.MatchDate, color = Color.Gray, fontSize = 12.sp)
            Text(text = fixture.MatchTime, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(
                text = fixture.Highlight ?: fixture.Status,
                color = Color(0xFFFFD700),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            if (fixture.TeamAscore != null && fixture.TeamBscore != null) {
                Text(
                    text = "${fixture.TeamAscore}  vs  ${fixture.TeamBscore}",
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
        }

        // Team B
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            TeamLogo(fixture.TeamBlogo)
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = fixture.TeamB,
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}