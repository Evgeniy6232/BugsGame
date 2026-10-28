package com.evgenii.bugsgame.ui.game

import com.evgenii.bugsgame.domain.model.Bug
import com.evgenii.bugsgame.domain.model.GameSettings
import kotlin.math.ceil

enum class GameStatus {
    IDLE,
    RUNNING,
    FINISHED
}

data class GameUiState(
    val status: GameStatus = GameStatus.IDLE,
    val bugs: List<Bug> = emptyList(),
    val score: Int = 0,
    val hits: Int = 0,
    val misses: Int = 0,
    val elapsedSec: Float = 0f,
    val spawnCooldown: Float = 0f,
    val settings: GameSettings = GameSettings(),
    val arenaWidth: Float = 0f,
    val arenaHeight: Float = 0f,
    val density: Float = 1f
) {
    val isRunning: Boolean get() = status == GameStatus.RUNNING
    val isFinished: Boolean get() = status == GameStatus.FINISHED

    val accuracyPercent: Int
        get() {
            val total = hits + misses
            return if (total == 0) 0 else hits * 100 / total
        }

    val remainingSec: Int
        get() = ceil(settings.roundDurationSec - elapsedSec).toInt().coerceAtLeast(0)

    val progress: Float
        get() = if (settings.roundDurationSec == 0) 0f
                else (elapsedSec / settings.roundDurationSec).coerceIn(0f, 1f)
}
