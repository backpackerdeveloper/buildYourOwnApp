package com.shubhamtripz.iplbubble.data.remote

import com.shubhamtripz.iplbubble.domain.models.HomeApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface HomeApiService {
    @GET
    suspend fun getHomeData(@Url url: String): List<HomeApiResponse>
}