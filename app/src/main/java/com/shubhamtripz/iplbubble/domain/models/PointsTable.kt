package com.shubhamtripz.iplbubble.domain.models

data class PointsTable(
    val Teams: String,
    val Played: Int,
    val Win: Int,
    val Loss: Int,
    val Tie: Int,
    val PTS: Int,
    val NRR: Double
)