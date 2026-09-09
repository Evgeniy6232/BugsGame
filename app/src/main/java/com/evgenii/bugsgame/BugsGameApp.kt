package com.evgenii.bugsgame

import android.app.Application
import com.evgenii.bugsgame.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BugsGameApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BugsGameApp)
            modules(appModule)
        }
    }
}
