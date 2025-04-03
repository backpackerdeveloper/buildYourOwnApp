package com.shubhamtripz.iplbubble.data.remote

import com.shubhamtripz.iplbubble.data.utils.STATS_API_ENDPOINT
import com.shubhamtripz.iplbubble.domain.models.PlayerStats
import retrofit2.http.GET

interface StatsApiService {
    @GET(STATS_API_ENDPOINT)
    suspend fun getPlayerStats(): List<PlayerStats>
}