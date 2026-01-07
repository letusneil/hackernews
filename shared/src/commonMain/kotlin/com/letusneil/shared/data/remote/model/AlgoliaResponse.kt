package com.letusneil.shared.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoriesResponse(
    val hits: List<HitResponse>,
    val nbPages: Int,
    val page: Int,
    val hitsPerPage: Int
)

@Serializable
data class StoryDetailResponse(
    val id: Long,
    val author: String? = null,
    val title: String? = null,
    val text: String? = null,
    val points: Int? = null,
    val url: String? = null,
    @SerialName("created_at_i") val createdAtI: Long,
    val children: List<StoryDetailResponse> = emptyList()
)