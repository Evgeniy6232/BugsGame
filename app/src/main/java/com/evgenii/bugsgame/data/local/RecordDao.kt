package com.evgenii.bugsgame.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query(
        """
        SELECT r.id AS id,
               r.playerId AS playerId,
               p.fullName AS playerName,
               r.score AS score,
               r.hits AS hits,
               r.misses AS misses,
               r.difficulty AS difficulty,
               r.speed AS speed,
               r.maxBugs AS maxBugs,
               r.roundSec AS roundSec,
               r.playedAt AS playedAt
        FROM records r
        INNER JOIN players p ON p.id = r.playerId
        ORDER BY r.score DESC, r.playedAt ASC
        """
    )
    fun observeTop(): Flow<List<RecordWithPlayer>>

    @Query("SELECT * FROM records WHERE playerId = :playerId LIMIT 1")
    suspend fun findByPlayer(playerId: Long): RecordEntity?

    @Insert
    suspend fun insert(record: RecordEntity)

    @Update
    suspend fun update(record: RecordEntity)
}
