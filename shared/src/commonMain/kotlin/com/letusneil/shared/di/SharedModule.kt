package com.letusneil.shared.di

import com.letusneil.shared.data.remote.HackerNewsApi
import com.letusneil.shared.data.remote.KtorHackerNewsApi
import com.letusneil.shared.data.repository.NewsRepository
import com.letusneil.shared.data.repository.NewsRepositoryImpl
import com.letusneil.shared.domain.usecase.GetTopStoriesUseCase
import com.letusneil.shared.presentation.home.HomeViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
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

    single<Clock> { Clock.System }

    // Data layer
    singleOf(::KtorHackerNewsApi) { bind<HackerNewsApi>() }
    singleOf(::NewsRepositoryImpl) { bind<NewsRepository>() }

    // Domain layer - Use Cases
    factoryOf(::GetTopStoriesUseCase)

    // Presentation layer - ViewModels
    viewModelOf(::HomeViewModel)
}

// Platform-specific module (Expect/Actual)
// expect val platformModule: org.koin.core.module.Module