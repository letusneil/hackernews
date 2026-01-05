package com.letusneil.shared.data.mapper

import com.letusneil.shared.data.remote.model.HitResponse
import com.letusneil.shared.domain.model.NewsCategory
import com.letusneil.shared.domain.model.NewsFeedItem
import com.letusneil.shared.domain.model.NewsItem
import com.letusneil.shared.util.formatTimeAgo
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal fun HitResponse.toDomain(category: NewsCategory): NewsFeedItem {
    return NewsFeedItem(
        newsItem = NewsItem(
            id = id,
            title = title ?: storyText?.take(50)?.let { "$it..." } ?: "Untitled",
            url = url,
            author = author ?: "Anonymous",
            points = points ?: 0,
            commentCount = commentCount ?: 0,
            timeAgo = createdAt?.let { formatTimeAgo(it) } ?: "",
        ),
        category = category
    )
}