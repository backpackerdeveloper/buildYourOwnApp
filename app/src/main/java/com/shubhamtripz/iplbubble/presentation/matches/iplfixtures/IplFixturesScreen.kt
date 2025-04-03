package com.shubhamtripz.iplbubble.presentation.matches.iplfixtures

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shubhamtripz.iplbubble.presentation.matches.iplfixtures.components.FixtureItem
import com.shubhamtripz.iplbubble.presentation.matches.iplfixtures.components.FixtureItemShimmer
import org.koin.androidx.compose.koinViewModel

@Composable
fun IplFixturesScreen(viewModel: IplFixturesViewModel = koinViewModel()) {
    val fixtureState by viewModel.fixtures.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        if (fixtureState.isEmpty()) {
            LazyColumn {
                items(10) {
                    FixtureItemShimmer()
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                }
            }
        } else {
            LazyColumn {
                items(fixtureState) { fixture ->
                    FixtureItem(fixture)
                    Divider(color = Color.Black, thickness = 0.5.dp)
                }
            }
        }
    }
}