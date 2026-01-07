package com.letusneil.shared.data.remote.fakes

import com.letusneil.shared.data.remote.HackerNewsApi
import com.letusneil.shared.data.remote.model.HitResponse
import com.letusneil.shared.data.remote.model.StoriesResponse

class FakeHackerNewsApi : HackerNewsApi {

    var lastRequestedPage: Int? = null
    var shouldThrowError: Boolean = false
    var lastRequestedTimestamp: Long? = null
    var responseHits: List<HitResponse> = listOf(
        HitResponse(
            id = "test_id",
            title = "Test Title",
            url = "",
            author = "",
            points = 21,
            commentCount = 42,
            createdAt = 33,
            tags = listOf("top"),
            storyText = "hello world"
        )
    )

    override suspend fun fetchTopStories(timestamp: Long, page: Int): StoriesResponse {
        lastRequestedPage = page
        lastRequestedTimestamp = timestamp
        if (shouldThrowError) throw Exception("Fake API Error")
        return StoriesResponse(hits = responseHits, nbPages = 12, page = 1, hitsPerPage = 12)
    }

    override suspend fun fetchByCategory(
        categoryTag: String,
        page: Int
    ): StoriesResponse {
        return StoriesResponse(hits = emptyList(), nbPages = 12, page = 0, hitsPerPage = 12)
    }
}