package com.n1n3b1t.thetest

import android.app.Application
import com.n1n3b1t.thetest.main.di.mainModule
import com.n1n3b1t.thetest.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    networkModule,
                    appModule,
                    mainModule
                )
            )
        }
    }
}