package com.shubhamtripz.iplbubble.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shubhamtripz.iplbubble.domain.models.HomeApiResponse

@Entity(tableName = "home_responses")
data class HomeResponseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val featuredVideoTitle: String,
    val featuredVideoThumbnail: String,
    val featuredVideoURL: String,
    val tp_imageUrl: String,
    val tp_title: String,
    val tp_post_time: String,
    val tp_post_description: String,
    val timestamp: Long = System.currentTimeMillis() // To track when the entry was cached
)

// Extension functions to convert between entity and domain model
fun HomeResponseEntity.toDomainModel(): HomeApiResponse {
    return HomeApiResponse(
        featuredVideoTitle = featuredVideoTitle,
        featuredVideoThumbnail = featuredVideoThumbnail,
        featuredVideoURL = featuredVideoURL,
        tp_imageUrl = tp_imageUrl,
        tp_title = tp_title,
        tp_post_time = tp_post_time,
        tp_post_description = tp_post_description
    )
}

fun HomeApiResponse.toEntity(): HomeResponseEntity {
    return HomeResponseEntity(
        featuredVideoTitle = featuredVideoTitle,
        featuredVideoThumbnail = featuredVideoThumbnail,
        featuredVideoURL = featuredVideoURL,
        tp_imageUrl = tp_imageUrl,
        tp_title = tp_title,
        tp_post_time = tp_post_time,
        tp_post_description = tp_post_description
    )
}
