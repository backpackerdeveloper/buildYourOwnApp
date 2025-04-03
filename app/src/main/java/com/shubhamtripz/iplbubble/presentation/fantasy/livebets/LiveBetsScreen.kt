package com.shubhamtripz.iplbubble.presentation.fantasy.livebets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.shubhamtripz.iplbubble.domain.models.Bet
import com.shubhamtripz.iplbubble.presentation.fantasy.livebets.components.BetCard

// Feature under Construction

@Composable
fun LiveBetsScreen() {
    val bets = listOf(
        Bet("1", "Who will win IPL 2025?", "CSK", "RCB", 120),
        Bet("2", "Who will be the Orange Cap winner?", "Virat Kohli", "David Warner", 85),
        Bet("3", "Which team will score the highest total?", "MI", "KKR", 95)
    )

    var showDialog by remember { mutableStateOf(false) }

    LazyColumn {
        items(bets) { bet ->
            BetCard(bet) { selectedOption ->
                showDialog = true
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Coming Soon") },
            text = { Text(text = "This feature is under development. Stay tuned!") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}