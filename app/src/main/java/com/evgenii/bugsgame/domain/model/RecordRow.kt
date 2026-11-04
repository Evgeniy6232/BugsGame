package com.evgenii.bugsgame.domain.model

import java.time.LocalDate

data class RecordRow(
    val id: Long,
    val playerId: Long,
    val playerName: String,
    val score: Int,
    val hits: Int,
    val misses: Int,
    val difficulty: Difficulty,
    val speed: Int,
    val maxBugs: Int,
    val roundSec: Int,
    val playedAt: LocalDate
)
