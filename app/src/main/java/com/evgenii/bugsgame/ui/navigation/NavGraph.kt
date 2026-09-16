package com.evgenii.bugsgame.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.evgenii.bugsgame.ui.registration.RegistrationScreen
import com.evgenii.bugsgame.ui.registration.RegistrationViewModel
import com.evgenii.bugsgame.ui.result.ResultScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    registrationViewModel: RegistrationViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.REGISTRATION
    ) {
        composable(Routes.REGISTRATION) {
            RegistrationScreen(
                viewModel = registrationViewModel,
                onNavigateToResult = { navController.navigate(Routes.RESULT) }
            )
        }

        composable(Routes.RESULT) {
            val uiState by registrationViewModel.uiState.collectAsStateWithLifecycle()
            ResultScreen(player = uiState.result)
        }
    }
}