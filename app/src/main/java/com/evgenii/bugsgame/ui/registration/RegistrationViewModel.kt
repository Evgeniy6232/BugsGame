package com.evgenii.bugsgame.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgenii.bugsgame.data.repository.PlayerRepository
import com.evgenii.bugsgame.domain.model.Difficulty
import com.evgenii.bugsgame.domain.model.Gender
import com.evgenii.bugsgame.domain.model.Player
import com.evgenii.bugsgame.domain.model.SavedPlayer
import com.evgenii.bugsgame.domain.usecase.GetZodiacSignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class RegistrationViewModel(
    private val getZodiacSignUseCase: GetZodiacSignUseCase,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    init {
        playerRepository.players
            .onEach { players -> _uiState.update { it.copy(savedPlayers = players) } }
            .launchIn(viewModelScope)
    }

    fun onFullNameChange(value: String) {
        _uiState.update {
            it.copy(fullName = value, nameError = false, selectedPlayerId = null)
        }
    }

    fun onGenderChange(gender: Gender) {
        _uiState.update {
            it.copy(gender = gender, genderError = false, selectedPlayerId = null)
        }
    }

    fun onCourseChange(course: String) {
        _uiState.update {
            it.copy(course = course, courseError = false, selectedPlayerId = null)
        }
    }

    fun onDifficultyChange(difficulty: Difficulty) {
        _uiState.update {
            it.copy(difficulty = difficulty, selectedPlayerId = null)
        }
    }

    fun onBirthDateChange(date: LocalDate) {
        _uiState.update {
            it.copy(birthDate = date, birthDateError = false, selectedPlayerId = null)
        }
    }

    fun onPlayerSelected(id: Long) {
        val player = _uiState.value.savedPlayers.firstOrNull { it.id == id } ?: return

        _uiState.update {
            it.copy(
                fullName = player.fullName,
                gender = player.gender,
                course = player.course,
                difficulty = player.difficulty,
                birthDate = player.birthDate,
                nameError = false,
                genderError = false,
                courseError = false,
                birthDateError = false,
                selectedPlayerId = player.id,
                currentPlayerId = player.id,
                result = player.toPlayer()
            )
        }
    }

    fun onNewPlayer() {
        _uiState.update {
            it.copy(
                fullName = "",
                gender = null,
                course = null,
                difficulty = Difficulty.MEDIUM,
                birthDate = null,
                nameError = false,
                genderError = false,
                courseError = false,
                birthDateError = false,
                selectedPlayerId = null,
                currentPlayerId = null,
                result = null
            )
        }
    }

    fun onSubmit(onSaved: () -> Unit) {
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

        val player = Player(
            fullName = state.fullName.trim(),
            gender = state.gender,
            course = state.course,
            difficulty = state.difficulty,
            birthDate = state.birthDate,
            zodiac = getZodiacSignUseCase(state.birthDate)
        )

        _uiState.update { it.copy(result = player) }

        viewModelScope.launch {
            val id = playerRepository.save(player.toSavedPlayer())
            _uiState.update {
                it.copy(selectedPlayerId = id, currentPlayerId = id)
            }
            onSaved()
        }
    }

    private fun Player.toSavedPlayer(): SavedPlayer = SavedPlayer(
        id = 0L,
        fullName = fullName,
        gender = gender,
        course = course,
        difficulty = difficulty,
        birthDate = birthDate
    )

    private fun SavedPlayer.toPlayer(): Player = Player(
        fullName = fullName,
        gender = gender,
        course = course,
        difficulty = difficulty,
        birthDate = birthDate,
        zodiac = getZodiacSignUseCase(birthDate)
    )
}
