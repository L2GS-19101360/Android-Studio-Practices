package com.example.cliwaves.utils

import android.app.Application
import com.example.cliwaves.dependency_injection.repositoryModule
import com.example.cliwaves.dependency_injection.viewModelModule
import org.koin.core.context.startKoin

class AppConfig : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(repositoryModule, viewModelModule))
        }
    }

}