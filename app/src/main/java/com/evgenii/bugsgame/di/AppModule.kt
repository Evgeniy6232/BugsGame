package com.evgenii.bugsgame.di

import com.evgenii.bugsgame.domain.usecase.GetZodiacSignUseCase
import com.evgenii.bugsgame.ui.registration.RegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { GetZodiacSignUseCase() }

    viewModel { RegistrationViewModel(get()) }
}
