package com.evgenii.bugsgame.ui.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.evgenii.bugsgame.domain.model.Bug
import com.evgenii.bugsgame.domain.model.BugType

private val BugType.bodyColor: Color
    get() = when (this) {
        BugType.COMMON -> Color(0xFF6D4C41)
        BugType.FAST -> Color(0xFFC62828)
        BugType.RARE -> Color(0xFFF9A825)
    }

private val BugType.accentColor: Color
    get() = when (this) {
        BugType.COMMON -> Color(0xFF3E2723)
        BugType.FAST -> Color(0xFF5D0F0F)
        BugType.RARE -> Color(0xFF4E342E)
    }

fun DrawScope.drawBug(bug: Bug) {
    val cx = bug.x
    val cy = bug.y
    val r = bug.radius

    val body = bug.type.bodyColor
    val accent = bug.type.accentColor
    val legWidth = r * 0.13f

    repeat(3) { index ->
        val dy = (index - 1) * r * 0.45f
        drawLine(
            color = accent,
            start = Offset(cx - r * 0.50f, cy + dy),
            end = Offset(cx - r * 0.98f, cy + dy - r * 0.26f),
            strokeWidth = legWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = accent,
            start = Offset(cx + r * 0.50f, cy + dy),
            end = Offset(cx + r * 0.98f, cy + dy - r * 0.26f),
            strokeWidth = legWidth,
            cap = StrokeCap.Round
        )
    }

    drawOval(
        color = body,
        topLeft = Offset(cx - r * 0.60f, cy - r * 0.52f),
        size = Size(r * 1.20f, r * 1.42f)
    )

    drawOval(
        color = body,
        topLeft = Offset(cx - r * 0.46f, cy - r * 0.92f),
        size = Size(r * 0.92f, r * 0.60f)
    )

    drawCircle(
        color = accent,
        radius = r * 0.30f,
        center = Offset(cx, cy - r * 0.88f)
    )

    drawLine(
        color = accent,
        start = Offset(cx - r * 0.10f, cy - r * 1.10f),
        end = Offset(cx - r * 0.42f, cy - r * 1.34f),
        strokeWidth = legWidth * 0.7f,
        cap = StrokeCap.Round
    )
    drawLine(
        color = accent,
        start = Offset(cx + r * 0.10f, cy - r * 1.10f),
        end = Offset(cx + r * 0.42f, cy - r * 1.34f),
        strokeWidth = legWidth * 0.7f,
        cap = StrokeCap.Round
    )
}
