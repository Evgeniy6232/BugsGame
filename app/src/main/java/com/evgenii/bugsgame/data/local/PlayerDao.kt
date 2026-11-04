package com.evgenii.bugsgame.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players ORDER BY fullName COLLATE NOCASE")
    fun observeAll(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE fullName = :fullName COLLATE NOCASE LIMIT 1")
    suspend fun findByName(fullName: String): PlayerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: PlayerEntity): Long
}
