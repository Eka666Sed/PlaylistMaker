package com.example.playlistmaker.app

import android.app.Application
import com.example.playlistmaker.di.databaseModule
import com.example.playlistmaker.di.interactorModule
import com.example.playlistmaker.di.navigationModule
import com.example.playlistmaker.di.networkModule
import com.example.playlistmaker.di.repositoryModule
import com.example.playlistmaker.di.sharedPreferencesModule
import com.example.playlistmaker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                interactorModule,
                navigationModule,
                networkModule,
                repositoryModule,
                sharedPreferencesModule,
                viewModelModule,
                databaseModule
            )
        }
    }
}