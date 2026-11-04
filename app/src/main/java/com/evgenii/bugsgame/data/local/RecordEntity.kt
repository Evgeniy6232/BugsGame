package com.evgenii.bugsgame.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "records",
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["playerId"], unique = true)]
)
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val playerId: Long,
    val score: Int,
    val hits: Int,
    val misses: Int,
    val difficulty: String,
    val speed: Int,
    val maxBugs: Int,
    val roundSec: Int,
    val playedAt: Long
)
