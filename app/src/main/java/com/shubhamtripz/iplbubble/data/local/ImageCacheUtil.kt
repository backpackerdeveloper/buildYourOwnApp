package com.shubhamtripz.iplbubble.data.local

import android.content.Context
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageCacheUtil(private val context: Context) {

    private val imageLoader = ImageLoader.Builder(context)
        .respectCacheHeaders(true)
        .build()

    /**
     * Preloads and caches images to disk
     */
    suspend fun cacheImages(imageUrls: List<String>) {
        withContext(Dispatchers.IO) {
            imageUrls.forEach { url ->
                try {
                    val request = ImageRequest.Builder(context)
                        .data(url)
                        .memoryCacheKey(url)
                        .diskCacheKey(url)
                        .build()

                    // This will force Coil to download and cache the image
                    imageLoader.execute(request)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // later use
    suspend fun isImageCached(imageUrl: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .memoryCacheKey(imageUrl)
                    .diskCacheKey(imageUrl)
                    .build()

                val result = imageLoader.execute(request)
                result is SuccessResult
            } catch (e: Exception) {
                false
            }
        }
    }
}