package com.evgenii.bugsgame.data.local

import com.evgenii.bugsgame.domain.model.Difficulty
import com.evgenii.bugsgame.domain.model.Gender
import com.evgenii.bugsgame.domain.model.RecordRow
import com.evgenii.bugsgame.domain.model.SavedPlayer
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

fun PlayerEntity.toDomain(): SavedPlayer = SavedPlayer(
    id = id,
    fullName = fullName,
    gender = gender.toGender(),
    course = course,
    difficulty = difficulty.toDifficulty(),
    birthDate = LocalDate.ofInstant(Instant.ofEpochMilli(birthDateMillis), ZoneOffset.UTC)
)

fun SavedPlayer.toEntity(): PlayerEntity = PlayerEntity(
    id = id,
    fullName = fullName.trim(),
    gender = gender.name,
    course = course,
    difficulty = difficulty.name,
    birthDateMillis = birthDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
)

fun RecordWithPlayer.toDomain(): RecordRow = RecordRow(
    id = id,
    playerId = playerId,
    playerName = playerName,
    score = score,
    hits = hits,
    misses = misses,
    difficulty = difficulty.toDifficulty(),
    speed = speed,
    maxBugs = maxBugs,
    roundSec = roundSec,
    playedAt = LocalDate.ofInstant(Instant.ofEpochMilli(playedAt), ZoneOffset.UTC)
)

private fun String.toGender(): Gender =
    Gender.entries.firstOrNull { it.name == this } ?: Gender.MAN

private fun String.toDifficulty(): Difficulty =
    Difficulty.entries.firstOrNull { it.name == this } ?: Difficulty.MEDIUM
