package com.shubhamtripz.iplbubble.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shubhamtripz.iplbubble.data.local.entity.HomeResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeResponses(responses: List<HomeResponseEntity>)

    @Query("SELECT * FROM home_responses ORDER BY timestamp DESC")
    fun getAllHomeResponses(): Flow<List<HomeResponseEntity>>

    @Query("SELECT * FROM home_responses ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestHomeResponses(): List<HomeResponseEntity>

    @Query("DELETE FROM home_responses")
    suspend fun clearAllHomeResponses()
}