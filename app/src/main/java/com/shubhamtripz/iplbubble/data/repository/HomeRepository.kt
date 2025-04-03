package com.shubhamtripz.iplbubble.data.repository

import com.shubhamtripz.iplbubble.data.local.ImageCacheUtil
import com.shubhamtripz.iplbubble.data.local.dao.HomeResponseDao
import com.shubhamtripz.iplbubble.data.local.entity.toDomainModel
import com.shubhamtripz.iplbubble.data.local.entity.toEntity
import com.shubhamtripz.iplbubble.data.remote.HomeApiService
import com.shubhamtripz.iplbubble.data.utils.HOME_SCREEN_API
import com.shubhamtripz.iplbubble.data.utils.NetworkUtils
import com.shubhamtripz.iplbubble.domain.models.HomeApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepository(
    private val apiService: HomeApiService,
    private val homeResponseDao: HomeResponseDao,
    private val imageCacheUtil: ImageCacheUtil,
    private val networkUtils: NetworkUtils
) {
    /**
     * Fetches data with caching strategy:
     * 1. If online, fetch from API and update cache
     * 2. If offline, return cached data
     */
    suspend fun fetchHomeData(): List<HomeApiResponse> {
        return if (networkUtils.isNetworkAvailable()) {
            try {
                val apiUrl = HOME_SCREEN_API
                val apiResponse = apiService.getHomeData(apiUrl)

                homeResponseDao.insertHomeResponses(apiResponse.map { it.toEntity() })
                cacheImagesFromResponse(apiResponse)
                apiResponse

            } catch (e: Exception) {
                // If API call fails, fall back to cache
                homeResponseDao.getLatestHomeResponses().map { it.toDomainModel() }
            }
        } else {
            // Offline: Return cached data
            homeResponseDao.getLatestHomeResponses().map { it.toDomainModel() }
        }
    }

    fun getHomeDataStream(): Flow<List<HomeApiResponse>> {
        return homeResponseDao.getAllHomeResponses().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    /**
     * Extract image URLs from response and cache them
     */
    private suspend fun cacheImagesFromResponse(responses: List<HomeApiResponse>) {
        val imageUrls = responses.flatMap { response ->
            listOf(
                response.featuredVideoThumbnail,
                response.tp_imageUrl
            )
        }.distinct()

        imageCacheUtil.cacheImages(imageUrls)
    }
}