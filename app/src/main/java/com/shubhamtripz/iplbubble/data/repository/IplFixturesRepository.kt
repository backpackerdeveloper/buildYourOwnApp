package com.shubhamtripz.iplbubble.data.repository

import com.shubhamtripz.iplbubble.data.remote.IplFixturesApiService
import com.shubhamtripz.iplbubble.domain.models.IplFixture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IplFixturesRepository(private val apiService: IplFixturesApiService) {
    fun getIplFixtures(): Flow<List<IplFixture>> = flow {
        emit(apiService.getFixtures())
    }
}