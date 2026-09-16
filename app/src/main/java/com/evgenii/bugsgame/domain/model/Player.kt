package com.evgenii.bugsgame.domain.model

import java.time.LocalDate

data class Player(
    val fullName: String,
    val gender: Gender,
    val course: String,
    val difficulty: Difficulty,
    val birthDate: LocalDate,
    val zodiac: Zodiac
)