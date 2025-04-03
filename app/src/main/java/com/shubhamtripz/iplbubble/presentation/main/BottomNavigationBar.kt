package com.shubhamtripz.iplbubble.presentation.main

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shubhamtripz.iplbubble.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items: List<Pair<Screen, Int>> = listOf(
        Pair(Screen.Home, R.drawable.home_24),
        Pair(Screen.Matches, R.drawable.ic_matches),
        Pair(Screen.Fantasy, R.drawable.ic_fantasy)
    )

    NavigationBar(containerColor = Color(0xFF1A1A2E)) {
        items.forEach { (screen, iconRes) ->
            val isSelected = navController.currentDestination?.route == screen.route

            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = isSelected,
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}

