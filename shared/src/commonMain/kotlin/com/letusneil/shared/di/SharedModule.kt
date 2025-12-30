package com.letusneil.shared.di

import com.letusneil.shared.data.remote.HackerNewsApi
import com.letusneil.shared.data.repository.NewsRepository
import com.letusneil.shared.data.repository.NewsRepositoryImpl
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    singleOf(::HackerNewsApi)
    singleOf(::NewsRepositoryImpl) { bind<NewsRepository>() }
}

// Platform-specific module (Expect/Actual)
// expect val platformModule: org.koin.core.module.Module