package com.letusneil.shared.di

import com.letusneil.shared.data.remote.HackerNewsApi
import com.letusneil.shared.data.remote.KtorHackerNewsApi
import com.letusneil.shared.data.repository.StoriesRepository
import com.letusneil.shared.data.repository.StoriesRepositoryImpl
import com.letusneil.shared.domain.usecase.GetTopStoriesUseCase
import com.letusneil.shared.presentation.home.HomeViewModel
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val platformModule = module {
    singleOf(::AppDispatchersImpl) { bind<AppDispatchers>() }
    single<Clock> { Clock.System }
}

val networkModule = module {
    // We use a factory function for clearer configuration
    single { provideHttpClient() }
}

@OptIn(ExperimentalTime::class)
val dataModule = module {
    singleOf(::KtorHackerNewsApi) { bind<HackerNewsApi>() }
    singleOf(::StoriesRepositoryImpl) { bind<StoriesRepository>() }
}

val domainModule = module {
    factoryOf(::GetTopStoriesUseCase)
}

val presentationModule = module {
    viewModelOf(::HomeViewModel)
}

val sharedModule = module {
    includes(
        platformModule,
        networkModule,
        dataModule,
        domainModule,
        presentationModule
    )
}

private fun provideHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }

    install(Logging) {
        // Use Headers or Info. Avoid ALL in production (leaks sensitive data).
        level = LogLevel.HEADERS

        // Custom logger using Napier
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(tag = "Network", message = message)
            }
        }
    }
}

// Platform-specific module (Expect/Actual)
// expect val platformModule: org.koin.core.module.Module