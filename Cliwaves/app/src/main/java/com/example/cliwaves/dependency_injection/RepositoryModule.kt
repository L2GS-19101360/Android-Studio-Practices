package com.example.cliwaves.dependency_injection

import com.example.cliwaves.network.repository.WeatherDataRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherDataRepository() }
}