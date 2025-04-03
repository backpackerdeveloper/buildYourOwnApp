package com.shubhamtripz.iplbubble.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.shubhamtripz.iplbubble.domain.models.HomeApiResponse
import com.valentinilk.shimmer.shimmer

@Composable
fun FeaturedVideoCard(video: HomeApiResponse, context: Context) {
    val imageLoader = ImageLoader.Builder(context)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(video.featuredVideoThumbnail)
            .memoryCacheKey(video.featuredVideoThumbnail)
            .diskCacheKey(video.featuredVideoThumbnail)
            .build(),
        imageLoader = imageLoader
    )

    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.featuredVideoURL))
                context.startActivity(intent)
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = "Video Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Text(
                text = video.featuredVideoTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ShimmerFeaturedVideoCard() {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            // Shimmer Thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .shimmer()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Shimmer Title
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(0.8f)
                    .height(14.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .shimmer()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Shimmer Subtitle
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth(0.6f)
                    .height(12.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .shimmer()
            )
        }
    }
}
