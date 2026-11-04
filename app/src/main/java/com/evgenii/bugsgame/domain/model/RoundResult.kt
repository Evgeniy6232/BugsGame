package com.evgenii.bugsgame.domain.model

data class RoundResult(
    val playerId: Long,
    val score: Int,
    val hits: Int,
    val misses: Int,
    val settings: GameSettings,
    val playedAt: Long
)
