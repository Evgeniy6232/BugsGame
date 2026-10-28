package com.evgenii.bugsgame.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.evgenii.bugsgame.ui.main.MainScreen
import com.evgenii.bugsgame.ui.registration.RegistrationViewModel
import com.evgenii.bugsgame.ui.result.ResultScreen
import com.evgenii.bugsgame.ui.settings.SettingsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    registrationViewModel: RegistrationViewModel,
    settingsViewModel: SettingsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(
                registrationViewModel = registrationViewModel,
                settingsViewModel = settingsViewModel,
                onNavigateToResult = { navController.navigate(Routes.RESULT) }
            )
        }

        composable(Routes.RESULT) {
            val uiState by registrationViewModel.uiState.collectAsStateWithLifecycle()
            ResultScreen(player = uiState.result)
        }
    }
}