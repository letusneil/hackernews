package com.letusneil.shared.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class HackerNewsApi(private val client: HttpClient) {
    private val baseUrl = "https://hn.algolia.com/api/v1"

    suspend fun fetchTopStories(timestamp: Long, page: Int): AlgoliaNewsResponse {
        return client.get("$baseUrl/search") {
            parameter("tags", "story")
            parameter("numericFilters", "created_at_i>$timestamp,points>10")
            parameter("hitsPerPage", 15)
            parameter("page", page)
        }.body()
    }

    suspend fun fetchByCategory(categoryTag: String, page: Int): AlgoliaNewsResponse {
        return client.get("$baseUrl/search") {
            parameter("tags", categoryTag)
            parameter("hitsPerPage", 15)
            parameter("page", page)
        }.body()
    }
}