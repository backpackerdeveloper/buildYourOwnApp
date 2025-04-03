package com.shubhamtripz.iplbubble.domain.models

data class HomeApiResponse(
    val featuredVideoTitle: String,
    val featuredVideoThumbnail: String,
    val featuredVideoURL: String,
    val tp_imageUrl: String,
    val tp_title: String,
    val tp_post_time: String,
    val tp_post_description: String
)