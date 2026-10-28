package com.evgenii.bugsgame.domain.model

enum class BugType(
    val points: Int,
    val speedFactor: Float,
    val sizeFactor: Float,
    val spawnWeight: Int
) {
    COMMON(points = 1, speedFactor = 1.0f, sizeFactor = 1.0f, spawnWeight = 65),
    FAST(points = 3, speedFactor = 2.0f, sizeFactor = 0.7f, spawnWeight = 28),
    RARE(points = 5, speedFactor = 0.7f, sizeFactor = 1.4f, spawnWeight = 7)
}
