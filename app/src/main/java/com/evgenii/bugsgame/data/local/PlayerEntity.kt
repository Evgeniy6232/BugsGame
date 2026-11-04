package com.evgenii.bugsgame.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val fullName: String,
    val gender: String,
    val course: String,
    val difficulty: String,
    val birthDateMillis: Long
)
