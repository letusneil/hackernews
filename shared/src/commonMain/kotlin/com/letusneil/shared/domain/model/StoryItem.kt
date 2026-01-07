package com.letusneil.shared.domain.model

data class StoriesFeedItem(
    val storyItem: StoryItem,
    val category: StoryCategory
)

data class StoryItem(
    val id: String,
    val title: String,
    val url: String?,
    val author: String,
    val points: Int,
    val commentCount: Int,
    val timeAgo: String, // Human readable: "2 hours ago"
)

enum class StoryCategory {
    TOP, NEW, BEST, SHOW, ASK, JOB
}