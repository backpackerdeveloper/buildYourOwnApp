package com.shubhamtripz.iplbubble.domain.models

data class IplFixture(
    val MatchDate: String,
    val MatchTime: String,
    val TeamA: String,
    val TeamB: String,
    val Status: String,
    val TeamAscore: String?,
    val TeamBscore: String?,
    val TeamAlogo: String,
    val TeamBlogo: String,
    val Highlight: String?
)