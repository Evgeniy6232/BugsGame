package com.evgenii.bugsgame.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlayerEntity::class, RecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    abstract fun recordDao(): RecordDao
}
