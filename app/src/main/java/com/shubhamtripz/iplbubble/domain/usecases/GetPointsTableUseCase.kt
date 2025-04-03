package com.shubhamtripz.iplbubble.domain.usecases

import com.shubhamtripz.iplbubble.data.repository.PointsTableRepository
import com.shubhamtripz.iplbubble.domain.models.PointsTable
import kotlinx.coroutines.flow.Flow

class GetPointsTableUseCase(private val repository: PointsTableRepository) {
    fun execute(): Flow<List<PointsTable>> = repository.getPointsTable()
}