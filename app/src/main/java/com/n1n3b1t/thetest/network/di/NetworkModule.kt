package com.n1n3b1t.thetest.network.di

import com.n1n3b1t.thetest.network.TestAPI
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {
    single<TestAPI> { createRetrofit("https://hr-challenge.dev.tapyou.com/api/", get()) }
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }
}

private fun createRetrofit(baseUrl: String, json: Json): TestAPI {
    return retrofit2.Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(TestAPI::class.java)
}