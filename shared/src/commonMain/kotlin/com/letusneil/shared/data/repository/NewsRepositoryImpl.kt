package com.letusneil.shared.data.repository

import com.letusneil.shared.data.mapper.toDomain
import com.letusneil.shared.data.remote.HackerNewsApi
import com.letusneil.shared.di.AppDispatchers
import com.letusneil.shared.domain.model.NewsCategory
import com.letusneil.shared.domain.model.NewsFeedItem
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

interface NewsRepository {
    suspend fun getTopStories(page: Int): List<NewsFeedItem>
}

@OptIn(ExperimentalTime::class)
internal class NewsRepositoryImpl(
    private val api: HackerNewsApi,
    private val dispatchers: AppDispatchers,
    private val clock: Clock = Clock.System
) : NewsRepository {

    override suspend fun getTopStories(page: Int): List<NewsFeedItem> = withContext(dispatchers.io) {
        val fortyEightHoursAgo = clock.now().epochSeconds - (48 * 60 * 60)
        val response = api.fetchTopStories(fortyEightHoursAgo, page)
        response.hits.map { it.toDomain(NewsCategory.TOP) }
    }
}