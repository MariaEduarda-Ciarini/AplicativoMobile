package br.com.fiap.climaapp.utils

import android.app.Application
import br.com.fiap.climaapp.dependecy_injection.networkModule
import br.com.fiap.climaapp.dependecy_injection.repositoryModule
import br.com.fiap.climaapp.dependecy_injection.serializerModule
import br.com.fiap.climaapp.dependecy_injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import br.com.fiap.climaapp.dependecy_injection.storageModule

class AppConfig : Application() {

    override fun onCreate(){
        super.onCreate()
        startKoin{
            androidContext(this@AppConfig)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    serializerModule,
                    storageModule,
                    networkModule
                )
            )
        }
    }
}