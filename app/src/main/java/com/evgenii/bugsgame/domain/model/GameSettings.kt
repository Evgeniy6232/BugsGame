package com.evgenii.bugsgame.domain.model

data class GameSettings(
    val speed: Int = 2,
    val maxBugs: Int = 10,
    val bonusIntervalSec: Int = 15,
    val roundDurationSec: Int = 45
) {
    companion object {
        val SPEED_RANGE = 1..5
        val MAX_BUGS_RANGE = 5..20
        val BONUS_INTERVAL_RANGE = 10..30
        val ROUND_DURATION_RANGE = 30..60
    }
}