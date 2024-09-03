package br.com.fiap.climaapp.dependecy_injection

import br.com.fiap.climaapp.storage.SharedPreferencesManager
import org.koin.dsl.module

val storageModule = module{
    single{
        SharedPreferencesManager(context = get(),gson = get())

    }
}