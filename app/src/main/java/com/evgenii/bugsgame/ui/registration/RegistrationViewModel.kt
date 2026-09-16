package com.evgenii.bugsgame.ui.registration

import androidx.lifecycle.ViewModel
import com.evgenii.bugsgame.domain.model.*
import com.evgenii.bugsgame.domain.usecase.GetZodiacSignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class RegistrationViewModel (
    private val getZodiacSignUseCase: GetZodiacSignUseCase
) : ViewModel {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onFullNameChange(value: String) {
        _uiState.update { it.copy(fullName = value, nameError = false) }
    }

    fun onGenderChange(gender: Gender) {
        _uiState.update { it.copy(gender = gender, genderError = false) }
    }

    fun onCourseChange(course: String) {
        _uiState.update { it.copy(course = course, courseError = false) }
    }

    fun onDifficultyChange(difficulty: Difficulty) {
        _uiState.update { it.copy(difficulty = difficulty) }
    }

    fun onBirthDateChange(date: LocalDate) {
        _uiState.update { it.copy(birthDate = date, birthDateError = false) }
    }

    fun onSubmit() {
        val state = _uiState.value

        val nameError = state.fullName.isBlank()
        val genderError = state.gender == null
        val courseError = state.course == null
        val birthDateError = state.birthDate == null

        if (nameError || genderError || courseError || birthDateError) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    genderError = genderError,
                    courseError = courseError,
                    birthDateError = birthDateError
                )
            }
            return
        }

        val zodiac = getZodiacSignUseCase(state.birthDate)

        val player = Player(
            fullName = state.fullName.trim(),
            gender = state.gender,
            course = state.course,
            difficulty = state.difficulty,
            birthDate = state.birthDate,
            zodiac = zodiac

        )

        _uiState.update { it.copy(result = player) }
    }
}