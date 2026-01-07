package com.letusneil.shared.data.mapper

import com.letusneil.shared.data.remote.model.HitResponse
import com.letusneil.shared.domain.model.StoryCategory
import com.letusneil.shared.domain.model.StoriesFeedItem
import com.letusneil.shared.domain.model.StoryItem
import com.letusneil.shared.util.formatTimeAgo
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal fun HitResponse.toDomain(category: StoryCategory): StoriesFeedItem {
    return StoriesFeedItem(
        storyItem = StoryItem(
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