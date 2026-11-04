package com.evgenii.bugsgame.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.ui.authors.AuthorsScreen
import com.evgenii.bugsgame.ui.records.RecordsScreen
import com.evgenii.bugsgame.ui.registration.RegistrationScreen
import com.evgenii.bugsgame.ui.registration.RegistrationViewModel
import com.evgenii.bugsgame.ui.rules.RulesScreen
import com.evgenii.bugsgame.ui.settings.SettingsScreen
import com.evgenii.bugsgame.ui.settings.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    registrationViewModel: RegistrationViewModel,
    settingsViewModel: SettingsViewModel,
    onNavigateToResult: () -> Unit,
    onStartGame: () -> Unit
) {
    val tabs = MainTab.entries
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    val registration by registrationViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        PrimaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = {
                        Text(
                            text = stringResource(tab.titleRes),
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (tabs[page]) {
                MainTab.FORM -> RegistrationScreen(
                    viewModel = registrationViewModel,
                    onNavigateToResult = onNavigateToResult
                )
                MainTab.RULES -> RulesScreen()
                MainTab.AUTHORS -> AuthorsScreen()
                MainTab.SETTINGS -> SettingsScreen(viewModel = settingsViewModel)
                MainTab.RECORDS -> RecordsScreen()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(
                onClick = onStartGame,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.nav_start_game))
            }

            if (registration.currentPlayerId == null) {
                Text(
                    text = stringResource(R.string.guest_hint),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
