package com.letusneil.shared.data.repository

import com.letusneil.shared.data.mapper.toDomain
import com.letusneil.shared.data.remote.HackerNewsApi
import com.letusneil.shared.di.AppDispatchers
import com.letusneil.shared.domain.model.StoryCategory
import com.letusneil.shared.domain.model.StoriesFeedItem
import com.letusneil.shared.domain.model.StoryDetail
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

interface StoriesRepository {
    suspend fun getTopStories(page: Int): List<StoriesFeedItem>
    suspend fun getStoryDetail(storyId: String): StoryDetail
}

@OptIn(ExperimentalTime::class)
internal class StoriesRepositoryImpl(
    private val api: HackerNewsApi,
    private val dispatchers: AppDispatchers,
    private val clock: Clock = Clock.System
) : StoriesRepository {

    override suspend fun getTopStories(page: Int): List<StoriesFeedItem> = withContext(dispatchers.io) {
        val fortyEightHoursAgo = clock.now().epochSeconds - (48 * 60 * 60)
        val response = api.fetchTopStories(fortyEightHoursAgo, page)
        response.hits.map { it.toDomain(StoryCategory.TOP) }
    }

    override suspend fun getStoryDetail(storyId: String): StoryDetail {
        val storyDto = api.fetchItem(storyId) // You likely need to add fetchItem(id) to your API

        // Fetch top-level comments in parallel
        val comments = storyDto.kids?.map { commentId ->
            async { fetchCommentTree(commentId) }
        }?.awaitAll() ?: emptyList()

        return StoryDetail(
            story = storyDto.toDomain(NewsCategory.TOP), // Reuse your mapper
            comments = comments
        )
    }
}