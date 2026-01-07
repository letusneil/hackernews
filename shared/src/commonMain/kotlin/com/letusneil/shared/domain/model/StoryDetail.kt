package com.letusneil.shared.domain.model

data class StoryDetail(
    val story: StoriesFeedItem,
    val comments: List<Comment>
)

data class Comment(
    val id: String,
    val author: String,
    val text: String, // HTML content
    val timeAgo: String,
    val childComments: List<Comment> // Recursive structure
)