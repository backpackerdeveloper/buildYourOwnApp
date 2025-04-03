package com.shubhamtripz.iplbubble.data.repository

import com.shubhamtripz.iplbubble.data.remote.PointsTableApiService
import com.shubhamtripz.iplbubble.domain.models.PointsTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PointsTableRepository(private val apiService: PointsTableApiService) {
    fun getPointsTable(): Flow<List<PointsTable>> = flow {
        emit(apiService.getPointsTable())
    }
}