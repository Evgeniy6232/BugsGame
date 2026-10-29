package com.evgenii.bugsgame.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.ui.authors.AuthorsScreen
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

    Column(modifier = Modifier.fillMaxSize()) {

        PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = { Text(stringResource(tab.titleRes)) }
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
            }
        }

        Button(
            onClick = onStartGame,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.nav_start_game))
        }
    }
}