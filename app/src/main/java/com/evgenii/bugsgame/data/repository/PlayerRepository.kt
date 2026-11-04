package com.evgenii.bugsgame.data.repository

import com.evgenii.bugsgame.data.local.PlayerDao
import com.evgenii.bugsgame.data.local.toDomain
import com.evgenii.bugsgame.data.local.toEntity
import com.evgenii.bugsgame.domain.model.SavedPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerRepository(private val dao: PlayerDao) {

    val players: Flow<List<SavedPlayer>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    suspend fun save(player: SavedPlayer): Long {
        val existing = dao.findByName(player.fullName.trim())
        val entity = player.toEntity().let {
            if (existing != null) it.copy(id = existing.id) else it
        }
        return dao.insert(entity)
    }
}
