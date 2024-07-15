package com.samfonsec.etherwallet

import android.app.Application
import com.samfonsec.etherwallet.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class EtherWalletApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@EtherWalletApplication)
            modules(appModule)
        }
    }
}