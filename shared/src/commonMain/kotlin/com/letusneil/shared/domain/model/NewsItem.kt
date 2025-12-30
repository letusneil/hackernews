package com.letusneil.shared.domain.model

data class NewsFeedItem(
    val newsItem: NewsItem,
    val category: NewsCategory
)

data class NewsItem(
    val id: String,
    val title: String,
    val url: String?,
    val author: String,
    val points: Int,
    val commentCount: Int,
    val timeAgo: String, // Human readable: "2 hours ago"
)

enum class NewsCategory {
    TOP, NEW, BEST, SHOW, ASK, JOB
}