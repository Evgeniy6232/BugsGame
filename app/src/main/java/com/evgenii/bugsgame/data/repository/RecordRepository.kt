package com.evgenii.bugsgame.data.repository

import com.evgenii.bugsgame.data.local.RecordDao
import com.evgenii.bugsgame.data.local.RecordEntity
import com.evgenii.bugsgame.data.local.toDomain
import com.evgenii.bugsgame.domain.model.RecordRow
import com.evgenii.bugsgame.domain.model.RoundResult
import com.evgenii.bugsgame.domain.model.roundDifficulty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecordRepository(private val dao: RecordDao) {

    val top: Flow<List<RecordRow>> =
        dao.observeTop().map { list -> list.map { it.toDomain() } }

    suspend fun saveIfRecord(result: RoundResult): Boolean {
        val old = dao.findByPlayer(result.playerId)
        val entity = result.toEntity()

        return when {
            old == null -> {
                dao.insert(entity)
                true
            }

            entity.score > old.score -> {
                dao.update(entity.copy(id = old.id))
                true
            }

            else -> false
        }
    }

    private fun RoundResult.toEntity(): RecordEntity = RecordEntity(
        playerId = playerId,
        score = score,
        hits = hits,
        misses = misses,
        difficulty = settings.roundDifficulty().name,
        speed = settings.speed,
        maxBugs = settings.maxBugs,
        roundSec = settings.roundDurationSec,
        playedAt = playedAt
    )
}
