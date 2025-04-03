package com.shubhamtripz.iplbubble.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shubhamtripz.iplbubble.data.local.dao.HomeResponseDao
import com.shubhamtripz.iplbubble.data.local.entity.HomeResponseEntity

@Database(entities = [HomeResponseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeResponseDao(): HomeResponseDao
}