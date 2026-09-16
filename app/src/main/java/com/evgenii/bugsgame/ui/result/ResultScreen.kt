package com.evgenii.bugsgame.ui.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.domain.model.Player
import com.evgenii.bugsgame.ui.common.difficultyLabel
import com.evgenii.bugsgame.ui.common.genderLabel
import com.evgenii.bugsgame.ui.common.zodiacLabel
import com.evgenii.bugsgame.ui.common.zodiacRes
import java.time.format.DateTimeFormatter

@Composable
fun ResultScreen(player: Player?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.result_title),
            style = MaterialTheme.typography.headlineMedium
        )

        if (player == null) {
            Text(stringResource(R.string.no_data))
        } else {
            ResultRow(stringResource(R.string.result_full_name), player.fullName)
            ResultRow(stringResource(R.string.result_gender), genderLabel(player.gender))
            ResultRow(stringResource(R.string.result_course), player.course)
            ResultRow(stringResource(R.string.result_difficulty), difficultyLabel(player.difficulty))
            ResultRow(
                stringResource(R.string.result_birth_date),
                player.birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            )
            ResultRow(stringResource(R.string.result_zodiac), zodiacLabel(player.zodiac))

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(zodiacRes(player.zodiac)),
                contentDescription = zodiacLabel(player.zodiac),
                modifier = Modifier.size(160.dp)
            )
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}