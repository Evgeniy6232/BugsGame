package com.evgenii.bugsgame.data.local

data class RecordWithPlayer(
    val id: Long,
    val playerId: Long,
    val playerName: String,
    val score: Int,
    val hits: Int,
    val misses: Int,
    val difficulty: String,
    val speed: Int,
    val maxBugs: Int,
    val roundSec: Int,
    val playedAt: Long
)
