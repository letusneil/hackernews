package com.letusneil.shared.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HitResponse(
    @SerialName("objectID") val id: String,
    val title: String? = null,
    val url: String? = null,
    val author: String? = null,
    val points: Int? = null,
    @SerialName("num_comments") val commentCount: Int? = null,
    @SerialName("created_at_i") val createdAt: Long? = null,
    @SerialName("_tags") val tags: List<String> = emptyList(),
    @SerialName("story_text") val storyText: String? = null
)