package com.girendi.flicknest.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.girendi.flicknest.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class FlickNestDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}