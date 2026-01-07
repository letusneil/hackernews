package com.letusneil.shared.domain.usecase

import com.letusneil.shared.data.repository.NewsRepository
import com.letusneil.shared.domain.model.StoriesFeedItem
import io.github.aakira.napier.Napier

class GetTopStoriesUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(page: Int): Result<List<StoriesFeedItem>> {
        return runCatching {
            Napier.d { "Fetching top stories for page $page" }
            newsRepository.getTopStories(page)
        }
    }
}