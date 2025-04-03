package com.shubhamtripz.iplbubble.data.remote


import com.shubhamtripz.iplbubble.data.utils.IPL_FIXTURES_ENDPOINT
import com.shubhamtripz.iplbubble.domain.models.IplFixture
import retrofit2.http.GET

interface IplFixturesApiService {
    @GET(IPL_FIXTURES_ENDPOINT)
    suspend fun getFixtures(): List<IplFixture>
}