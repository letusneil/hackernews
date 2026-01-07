package com.letusneil.hackernews.navigation

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class StoryDetailRoute(
    val storyId: String,
    val title: String,
    val url: String?,
    val author: String,
    val points: Int,
    val commentCount: Int,
    val timeAgo: String
)
