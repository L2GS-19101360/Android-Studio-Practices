package com.example.cliwaves.dependency_injection

import com.example.cliwaves.storage.SharedPreferencesManager
import org.koin.dsl.module

val storageModule = module {
    single { SharedPreferencesManager(context = get(), gson = get()) }
}