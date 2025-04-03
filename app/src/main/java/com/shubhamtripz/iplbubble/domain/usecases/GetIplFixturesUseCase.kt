package com.shubhamtripz.iplbubble.domain.usecases

import com.shubhamtripz.iplbubble.data.repository.IplFixturesRepository
import com.shubhamtripz.iplbubble.domain.models.IplFixture
import kotlinx.coroutines.flow.Flow

class GetIplFixturesUseCase(private val repository: IplFixturesRepository) {
    fun execute(): Flow<List<IplFixture>> = repository.getIplFixtures()
}