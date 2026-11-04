package com.evgenii.bugsgame.ui.registration

import com.evgenii.bugsgame.domain.model.Difficulty
import com.evgenii.bugsgame.domain.model.Gender
import com.evgenii.bugsgame.domain.model.Player
import com.evgenii.bugsgame.domain.model.SavedPlayer
import java.time.LocalDate

data class RegistrationUiState(

    val fullName: String = "",
    val gender: Gender? = null,
    val course: String? = null,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val birthDate: LocalDate? = null,

    val nameError: Boolean = false,
    val genderError: Boolean = false,
    val courseError: Boolean = false,
    val birthDateError: Boolean = false,

    val savedPlayers: List<SavedPlayer> = emptyList(),
    val selectedPlayerId: Long? = null,
    val currentPlayerId: Long? = null,

    val result: Player? = null
)
