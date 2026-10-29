package com.evgenii.bugsgame.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.domain.model.GameSettings
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.headlineMedium
        )

        SettingSlider(
            title = stringResource(R.string.setting_speed),
            value = settings.speed,
            range = GameSettings.SPEED_RANGE,
            onValueChange = viewModel::onSpeedChange
        )

        SettingSlider(
            title = stringResource(R.string.setting_max_bugs),
            value = settings.maxBugs,
            range = GameSettings.MAX_BUGS_RANGE,
            unit = stringResource(R.string.unit_pieces),
            onValueChange = viewModel::onMaxBugsChange
        )

        SettingSlider(
            title = stringResource(R.string.setting_bonus_interval),
            value = settings.bonusIntervalSec,
            range = GameSettings.BONUS_INTERVAL_RANGE,
            unit = stringResource(R.string.unit_seconds),
            onValueChange = viewModel::onBonusIntervalChange
        )

        SettingSlider(
            title = stringResource(R.string.setting_round_duration),
            value = settings.roundDurationSec,
            range = GameSettings.ROUND_DURATION_RANGE,
            unit = stringResource(R.string.unit_seconds),
            onValueChange = viewModel::onRoundDurationChange
        )
    }
}

@Composable
private fun SettingSlider(
    title: String,
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    unit: String = ""
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = if (unit.isEmpty()) "$value" else "$value $unit",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.roundToInt()) },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = range.last - range.first - 1
        )
    }
}