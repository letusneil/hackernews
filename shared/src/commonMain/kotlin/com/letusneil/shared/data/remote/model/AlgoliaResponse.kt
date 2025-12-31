package com.letusneil.shared.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val hits: List<HitResponse>,
    val nbPages: Int,
    val page: Int,
    val hitsPerPage: Int
)