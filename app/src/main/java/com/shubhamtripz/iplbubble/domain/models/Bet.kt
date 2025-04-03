package com.shubhamtripz.iplbubble.domain.models

data class Bet(
    val id: String,
    val question: String,
    val option1: String,
    val option2: String,
    val userCount: Int
)