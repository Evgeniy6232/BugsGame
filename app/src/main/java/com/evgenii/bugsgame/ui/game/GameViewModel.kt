package com.evgenii.bugsgame.ui.game

import androidx.lifecycle.ViewModel
import com.evgenii.bugsgame.domain.model.Bug
import com.evgenii.bugsgame.domain.model.BugType
import com.evgenii.bugsgame.domain.model.GameSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val random = Random.Default
    private var nextBugId = 1L

    fun startRound(settings: GameSettings, width: Float, height: Float, density: Float) {
        val state = _uiState.value

        if (state.isRunning) {
            _uiState.value = state.copy(
                arenaWidth = width,
                arenaHeight = height,
                density = density
            )
            return
        }

        nextBugId = 1L

        val fresh = GameUiState(
            status = GameStatus.RUNNING,
            settings = settings,
            arenaWidth = width,
            arenaHeight = height,
            density = density,
            spawnCooldown = 0f
        )

        val paddingPx = GameConfig.WALL_PADDING_DP * density
        val initialBugs = List(GameConfig.INITIAL_BUGS) { createBug(fresh, paddingPx) }

        _uiState.value = fresh.copy(bugs = initialBugs)
    }

    fun onFrame(dt: Float) {
        val state = _uiState.value
        if (!state.isRunning) return
        if (state.arenaWidth <= 0f || state.arenaHeight <= 0f) return

        val newElapsed = state.elapsedSec + dt

        if (newElapsed >= state.settings.roundDurationSec) {
            _uiState.value = state.copy(
                elapsedSec = state.settings.roundDurationSec.toFloat(),
                status = GameStatus.FINISHED
            )
            return
        }

        val paddingPx = GameConfig.WALL_PADDING_DP * state.density
        var bugs = state.bugs.map { bug ->
            moveWithBounce(bug, dt, state.arenaWidth, state.arenaHeight, paddingPx)
        }

        var cooldown = state.spawnCooldown - dt
        if (cooldown <= 0f && bugs.size < state.settings.maxBugs) {
            bugs = bugs + createBug(state, paddingPx)
            cooldown = GameConfig.SPAWN_INTERVAL_SEC
        }

        _uiState.value = state.copy(
            bugs = bugs,
            elapsedSec = newElapsed,
            spawnCooldown = cooldown.coerceAtLeast(0f)
        )
    }

    fun reset() {
        _uiState.value = GameUiState()
    }

    fun onTap(x: Float, y: Float) {
        val state = _uiState.value
        if (!state.isRunning) return

        val hit = state.bugs.lastOrNull { bug ->
            val dx = x - bug.x
            val dy = y - bug.y
            val hitRadius = bug.radius * GameConfig.HIT_RADIUS_FACTOR
            dx * dx + dy * dy <= hitRadius * hitRadius
        }

        _uiState.value = if (hit != null) {
            state.copy(
                bugs = state.bugs - hit,
                score = state.score + hit.points,
                hits = state.hits + 1
            )
        } else {
            state.copy(
                score = (state.score - GameConfig.MISS_PENALTY).coerceAtLeast(0),
                misses = state.misses + 1
            )
        }
    }

    private fun moveWithBounce(
        bug: Bug,
        dt: Float,
        width: Float,
        height: Float,
        paddingPx: Float
    ): Bug {
        var x = bug.x + bug.vx * dt
        var y = bug.y + bug.vy * dt
        var vx = bug.vx
        var vy = bug.vy

        val minX = paddingPx + bug.radius
        val maxX = width - paddingPx - bug.radius
        val minY = paddingPx + bug.radius
        val maxY = height - paddingPx - bug.radius

        if (x < minX) {
            x = minX
            vx = abs(vx)
        }
        if (x > maxX) {
            x = maxX
            vx = -abs(vx)
        }
        if (y < minY) {
            y = minY
            vy = abs(vy)
        }
        if (y > maxY) {
            y = maxY
            vy = -abs(vy)
        }

        return bug.copy(x = x, y = y, vx = vx, vy = vy)
    }

    private fun createBug(state: GameUiState, paddingPx: Float): Bug {
        val type = randomType()

        val sizePx = GameConfig.BASE_SIZE_DP * state.density * type.sizeFactor
        val radius = sizePx / 2f

        val minX = paddingPx + radius
        val maxX = state.arenaWidth - paddingPx - radius
        val minY = paddingPx + radius
        val maxY = state.arenaHeight - paddingPx - radius

        val x = if (maxX > minX) minX + random.nextFloat() * (maxX - minX) else state.arenaWidth / 2f
        val y = if (maxY > minY) minY + random.nextFloat() * (maxY - minY) else state.arenaHeight / 2f

        val angle = random.nextFloat() * 2f * PI.toFloat()

        val speedPx = GameConfig.BASE_SPEED_DP_PER_SEC * state.density *
                state.settings.speed * type.speedFactor

        return Bug(
            id = nextBugId++,
            type = type,
            x = x,
            y = y,
            vx = cos(angle) * speedPx,
            vy = sin(angle) * speedPx,
            size = sizePx,
            points = type.points
        )
    }

    private fun randomType(): BugType {
        val totalWeight = BugType.entries.sumOf { it.spawnWeight }
        var roll = random.nextInt(totalWeight)

        for (type in BugType.entries) {
            roll -= type.spawnWeight
            if (roll < 0) return type
        }
        return BugType.COMMON
    }
}
