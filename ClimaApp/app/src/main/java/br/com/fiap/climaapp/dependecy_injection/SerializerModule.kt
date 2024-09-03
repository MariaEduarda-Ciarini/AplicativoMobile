package br.com.fiap.climaapp.dependecy_injection

import org.koin.dsl.module
import org.koin.dsl.single
import com.google.gson.Gson

val serializerModule = module{
    single{Gson()}
}