package br.com.fiap.climaapp.dependecy_injection


import br.com.fiap.climaapp.fragments.home.location.HomeViewModel
import br.com.fiap.climaapp.fragments.home.location.LocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(weatherDataRepository = get()) }
    viewModel { LocationViewModel(weatherDataRepository = get()) }
}