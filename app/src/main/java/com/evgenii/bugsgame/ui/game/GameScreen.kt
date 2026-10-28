package com.evgenii.bugsgame.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.domain.model.GameSettings
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    settings: GameSettings,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val density = LocalDensity.current.density

    var arenaSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(arenaSize) {
        if (arenaSize.width > 0 && arenaSize.height > 0) {
            viewModel.startRound(
                settings = settings,
                width = arenaSize.width.toFloat(),
                height = arenaSize.height.toFloat(),
                density = density
            )
        }
    }

    GameLoop(
        isRunning = state.isRunning,
        onFrame = viewModel::onFrame
    )

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            GameHud(
                state = state,
                onExit = {
                    viewModel.reset()
                    onExit()
                }
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .onSizeChanged { arenaSize = it }
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            viewModel.onTap(offset.x, offset.y)
                        }
                    }
            ) {
                state.bugs.forEach { bug -> drawBug(bug) }
            }
        }

        if (state.isFinished) {
            GameResultPanel(
                state = state,
                onReplay = {
                    viewModel.startRound(
                        settings = settings,
                        width = arenaSize.width.toFloat(),
                        height = arenaSize.height.toFloat(),
                        density = density
                    )
                },
                onExit = {
                    viewModel.reset()
                    onExit()
                }
            )
        }
    }
}

@Composable
private fun GameHud(state: GameUiState, onExit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HudItem(
                label = stringResource(R.string.game_score),
                value = state.score.toString()
            )
            HudItem(
                label = stringResource(R.string.game_time),
                value = state.remainingSec.toString()
            )
            HudItem(
                label = stringResource(R.string.game_hits),
                value = "${state.hits} / ${state.hits + state.misses}"
            )
            IconButton(onClick = onExit) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.game_exit)
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { state.progress },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HudItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun GameResultPanel(
    state: GameUiState,
    onReplay: () -> Unit,
    onExit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.game_round_over),
                    style = MaterialTheme.typography.headlineSmall
                )

                ResultRow(
                    label = stringResource(R.string.game_score),
                    value = state.score.toString()
                )
                ResultRow(
                    label = stringResource(R.string.game_hits),
                    value = state.hits.toString()
                )
                ResultRow(
                    label = stringResource(R.string.game_misses),
                    value = state.misses.toString()
                )
                ResultRow(
                    label = stringResource(R.string.game_accuracy),
                    value = "${state.accuracyPercent}%"
                )

                Spacer(Modifier.height(4.dp))

                Button(
                    onClick = onReplay,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.game_play_again))
                }

                TextButton(
                    onClick = onExit,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.game_back_to_menu))
                }
            }
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun GameLoop(isRunning: Boolean, onFrame: (Float) -> Unit) {
    LaunchedEffect(isRunning) {
        if (!isRunning) return@LaunchedEffect

        var previous = withFrameNanos { it }

        while (true) {
            val current = withFrameNanos { it }
            val dt = ((current - previous) / 1_000_000_000f).coerceIn(0f, 0.05f)
            previous = current
            onFrame(dt)
        }
    }
}
