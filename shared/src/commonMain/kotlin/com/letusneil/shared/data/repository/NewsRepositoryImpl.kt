package com.letusneil.shared.data.repository

import com.letusneil.shared.data.mapper.toDomain
import com.letusneil.shared.data.remote.HackerNewsApi
import com.letusneil.shared.domain.model.NewsCategory
import com.letusneil.shared.domain.model.NewsFeedItem
import com.letusneil.shared.domain.model.NewsItem
import kotlinx.datetime.Clock

interface NewsRepository {
    suspend fun getTopStories(page: Int): List<NewsFeedItem>
}

internal class NewsRepositoryImpl(
    private val api: HackerNewsApi
) : NewsRepository {

    override suspend fun getTopStories(page: Int): List<NewsFeedItem> {
        // Calculate 48h ago timestamp
        val fortyEightHoursAgo = Clock.System.now().epochSeconds - (48 * 60 * 60)

        val response = api.fetchTopStories(fortyEightHoursAgo, page)
        return response.hits.map { it.toDomain(NewsCategory.TOP) }
    }
}