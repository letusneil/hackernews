package com.letusneil.shared.data.remote

import com.letusneil.shared.data.remote.model.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface HackerNewsApi {
    suspend fun fetchTopStories(timestamp: Long, page: Int): NewsResponse
    suspend fun fetchByCategory(categoryTag: String, page: Int): NewsResponse
}

internal class KtorHackerNewsApi(private val client: HttpClient) : HackerNewsApi {
    private val baseUrl = "https://hn.algolia.com/api/v1"

    override suspend fun fetchTopStories(timestamp: Long, page: Int): NewsResponse {
        return client.get("$baseUrl/search") {
            parameter("tags", "story")
            parameter("numericFilters", "created_at_i>$timestamp,points>10")
            parameter("hitsPerPage", 15)
            parameter("page", page)
        }.body()
    }

    override suspend fun fetchByCategory(categoryTag: String, page: Int): NewsResponse {
        return client.get("$baseUrl/search") {
            parameter("tags", categoryTag)
            parameter("hitsPerPage", 15)
            parameter("page", page)
        }.body()
    }
}