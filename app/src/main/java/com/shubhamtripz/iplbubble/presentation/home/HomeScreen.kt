package com.shubhamtripz.iplbubble.presentation.home

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shubhamtripz.iplbubble.R
import com.shubhamtripz.iplbubble.domain.models.MatchData
import com.shubhamtripz.iplbubble.domain.service.BubbleService
import com.shubhamtripz.iplbubble.presentation.home.livescore.MatchCard
import com.shubhamtripz.iplbubble.presentation.home.livescore.MatchViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(webView: WebView, navController: NavController) {
    val context = LocalContext.current
    val viewModel: MatchViewModel = getViewModel { parametersOf(webView) }
    val homeViewModel: HomeViewModel = getViewModel()

    val isBubbleEnabled by homeViewModel.isBubbleEnabled.collectAsState()
    val matchData by viewModel.matchData.collectAsState()
    val homeData by homeViewModel.homeData.collectAsState()

    val overlayPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (Settings.canDrawOverlays(context)) {
            homeViewModel.setBubbleEnabled(true)
            startBubbleService(context, matchData)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMatchDetails()
        homeViewModel.fetchHomeData()
    }

    LaunchedEffect(matchData) {
        if (isBubbleEnabled && matchData != null && Settings.canDrawOverlays(context)) {
            val intent = Intent(context, BubbleService::class.java).apply {
                putExtra("teamName", matchData?.teamName ?: "")
                putExtra("teamScore", matchData?.teamScore ?: "")
                putExtra("teamOver", matchData?.teamOver ?: "")
                putExtra("teamNameOponent", matchData?.teamNameOponent ?: "")
                putExtra("teamScoreOponent", matchData?.teamScoreOponent ?: "")
                putExtra("teamOverOponent", matchData?.teamOverOponent ?: "")
            }
            context.startService(intent)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("IPL Bubble", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { /* TODO: Open drawer */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ipl_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* TODO: Navigate to profile */ }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1A2E))
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(5.dp))

                MatchCard(
                    data = matchData ?: MatchData(
                        title = "Upcoming Match",
                        matchDate = "--:--",
                        teamName = "Team A",
                        teamImage = "",
                        teamScore = "-/-",
                        teamOver = "-.- ov",
                        teamNameOponent = "Team B",
                        teamImageOponent = "",
                        teamScoreOponent = "-/-",
                        teamOverOponent = "-.- ov",
                        matchHiLight = "scores will Live as soon IPL starts...",
                        venue = "TBD"
                    )
                )

                Spacer(modifier = Modifier.height(7.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isBubbleEnabled) "Disable IPL Bubble" else "Enable IPL Bubble",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )

                        Switch(
                            checked = isBubbleEnabled,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    if (Settings.canDrawOverlays(context)) {
                                        homeViewModel.setBubbleEnabled(true)
                                        startBubbleService(context, matchData)
                                    } else {
                                        val intent = Intent(
                                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                            Uri.parse("package:${context.packageName}")
                                        )
                                        overlayPermissionLauncher.launch(intent)
                                    }
                                } else {
                                    homeViewModel.setBubbleEnabled(false)
                                    val intent = Intent(context, BubbleService::class.java)
                                    context.stopService(intent)
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF4CAF50),
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color.Gray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(7.dp))
            }

            item {
                Text(
                    text = "Featured Videos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
                ) {
                    if (homeData.isEmpty()) {
                        items(5) { ShimmerFeaturedVideoCard() }
                    } else {
                        items(homeData) { item ->
                            FeaturedVideoCard(item, context)
                        }
                    }

                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Top Stories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
            }

            if (homeData.isEmpty()) {
                items(5) { TopStoryShimmerEffect() }
            } else {
                items(homeData) { item ->
                    TopStoryCard(item, context, navController)
                }
            }

        }
    }
}

private fun startBubbleService(context: android.content.Context, matchData: MatchData?) {
    val intent = Intent(context, BubbleService::class.java).apply {
        putExtra("teamName", matchData?.teamName ?: "")
        putExtra("teamScore", matchData?.teamScore ?: "")
        putExtra("teamOver", matchData?.teamOver ?: "")
        putExtra("teamNameOponent", matchData?.teamNameOponent ?: "")
        putExtra("teamScoreOponent", matchData?.teamScoreOponent ?: "")
        putExtra("teamOverOponent", matchData?.teamOverOponent ?: "")
    }
    context.startService(intent)
}