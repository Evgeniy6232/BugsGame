package com.evgenii.bugsgame.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.ui.registration.RegistrationScreen
import com.evgenii.bugsgame.ui.registration.RegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    registrationViewModel: RegistrationViewModel,
    onNavigateToResult: () -> Unit
) {
    // 1 список вкладок и сохран
    val tabs = MainTab.entries
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    // Скоп, чтоб свайпы работали, без корутина ляжет
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {

        // 3 Полоска вкладок
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

        // 4 часть с вкладками

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (tabs[page]) {
                MainTab.FORM -> RegistrationScreen(
                    viewModel = registrationViewModel,
                    onNavigateToResult = onNavigateToResult
                )
                MainTab.RULES -> TabStub(stringResource(R.string.rules_title))
                MainTab.AUTHORS -> TabStub(stringResource(R.string.authors_title))
                MainTab.SETTINGS -> TabStub(stringResource(R.string.settings_title))
            }
        }
    }
}

@Composable
private fun TabStub(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}