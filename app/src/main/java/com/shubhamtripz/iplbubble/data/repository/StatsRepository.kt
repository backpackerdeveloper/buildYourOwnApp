package com.shubhamtripz.iplbubble.data.repository

import com.shubhamtripz.iplbubble.data.remote.StatsApiService
import com.shubhamtripz.iplbubble.domain.models.PlayerStats

class StatsRepository(private val apiService: StatsApiService) {
    suspend fun getPlayerStats(): List<PlayerStats> {
        return apiService.getPlayerStats()
    }
}