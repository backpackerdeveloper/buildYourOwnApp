package com.shubhamtripz.iplbubble.presentation.home.livescore

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.shubhamtripz.iplbubble.domain.models.MatchData

@Composable
fun MatchCard(data: MatchData) {
    var showAnimation by remember { mutableStateOf(true) }

    LaunchedEffect(data) {
        showAnimation = true
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.shubhamtripz.iplbubble.R.raw.score_update_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = showAnimation,
        restartOnPlay = true
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = data.title, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = data.matchDate, fontWeight = FontWeight.Light, color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TeamInfo(name = data.teamName, score = data.teamScore, overs = data.teamOver)

                    Text(
                        text = "VS",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    TeamInfo(name = data.teamNameOponent, score = data.teamScoreOponent, overs = data.teamOverOponent)
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(data.matchHiLight, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Venue: ${data.venue}", color = Color.White)
            }
        }

        if (showAnimation && progress < 1f) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            )
        }
    }
}


@Composable
fun TeamInfo(name: String, score: String, overs: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(100.dp)
        )
        Text(text = score, color = Color.White, fontSize = 14.sp)
        Text(text = overs, color = Color.White, fontSize = 12.sp)
    }
}

