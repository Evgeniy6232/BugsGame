package com.evgenii.bugsgame.ui.settings

import androidx.lifecycle.ViewModel
import com.evgenii.bugsgame.domain.model.GameSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private val _settings = MutableStateFlow(GameSettings())
    val settings: StateFlow<GameSettings> = _settings.asStateFlow()

    fun onSpeedChange(value: Int) =
        _settings.update { it.copy(speed = value) }

    fun onMaxBugsChange(value: Int) =
        _settings.update { it.copy(maxBugs = value) }

    fun onBonusIntervalChange(value: Int) =
        _settings.update { it.copy(bonusIntervalSec = value) }

    fun onRoundDurationChange(value: Int) =
        _settings.update { it.copy(roundDurationSec = value) }
}