package br.com.fiap.climaapp.dependecy_injection

import br.com.fiap.climaapp.network.repository.WeatherDataRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherDataRepository(weatherAPI = get()) }
}