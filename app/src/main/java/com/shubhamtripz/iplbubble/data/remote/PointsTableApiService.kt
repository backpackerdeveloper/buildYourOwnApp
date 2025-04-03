package com.shubhamtripz.iplbubble.data.remote


import com.shubhamtripz.iplbubble.data.utils.POINTS_TABLE_ENDPOINT
import com.shubhamtripz.iplbubble.domain.models.PointsTable
import retrofit2.http.GET

interface PointsTableApiService {
    @GET(POINTS_TABLE_ENDPOINT)
    suspend fun getPointsTable(): List<PointsTable>
}