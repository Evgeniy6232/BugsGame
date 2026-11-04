package com.evgenii.bugsgame.di

import androidx.room.Room
import com.evgenii.bugsgame.data.local.AppDatabase
import com.evgenii.bugsgame.data.repository.PlayerRepository
import com.evgenii.bugsgame.data.repository.RecordRepository
import com.evgenii.bugsgame.domain.usecase.GetZodiacSignUseCase
import com.evgenii.bugsgame.ui.game.GameViewModel
import com.evgenii.bugsgame.ui.records.RecordsViewModel
import com.evgenii.bugsgame.ui.registration.RegistrationViewModel
import com.evgenii.bugsgame.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "bugsgame.db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    single { get<AppDatabase>().playerDao() }
    single { get<AppDatabase>().recordDao() }

    single { PlayerRepository(get()) }
    single { RecordRepository(get()) }

    factory { GetZodiacSignUseCase() }

    viewModel { RegistrationViewModel(get(), get()) }

    viewModel { SettingsViewModel() }

    viewModel { GameViewModel(get()) }

    viewModel { RecordsViewModel(get()) }
}
