package com.evgenii.bugsgame.domain.model

import java.time.LocalDate

data class SavedPlayer(
    val id: Long,
    val fullName: String,
    val gender: Gender,
    val course: String,
    val difficulty: Difficulty,
    val birthDate: LocalDate
)
