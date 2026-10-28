package com.evgenii.bugsgame.domain.model

import kotlin.math.hypot

data class Bug(
    val id: Long,
    val type: BugType,
    val x: Float,
    val y: Float,
    val vx: Float,
    val vy: Float,
    val size: Float,
    val points: Int
) {
    val speed: Float get() = hypot(vx, vy)

    val radius: Float get() = size / 2f
}
