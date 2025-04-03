package com.shubhamtripz.iplbubble.domain.models

data class MatchData(
    val title: String,
    val matchDate: String,
    val teamName: String,
    val teamScore: String,
    val teamOver: String,
    val teamImage: String,
    val teamNameOponent: String,
    val teamScoreOponent: String,
    val teamOverOponent: String,
    val teamImageOponent: String,
    val matchHiLight: String,
    val venue: String
)