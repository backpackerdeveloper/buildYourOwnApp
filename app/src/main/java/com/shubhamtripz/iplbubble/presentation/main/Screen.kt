package com.shubhamtripz.iplbubble.presentation.main

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Matches : Screen("matches", "Matches")
    object Fantasy : Screen("fantasy", "Fantasy")
}