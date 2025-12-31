package com.letusneil.shared.data.repository

import com.letusneil.shared.data.remote.fakes.FakeHackerNewsApi
import com.letusneil.shared.domain.model.NewsCategory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class NewsRepositoryTest {

    private val fixedNow = Instant.parse("2025-01-03T12:00:00Z")
    private val expectedTimestamp = Instant.parse("2025-01-01T12:00:00Z").epochSeconds

    @Test
    fun `getTopStories calculates correct timestamp and maps response`() = runTest {
        // ARRANGE
        val fakeApi = FakeHackerNewsApi()
        // Create a clock that ALWAYS returns our fixed time
        val fixedClock = object : Clock {
            override fun now(): Instant = fixedNow
        }

        val repository = NewsRepositoryImpl(
            api = fakeApi,
            clock = fixedClock
        )

        // ACT
        val result = repository.getTopStories(page = 1)

        // ASSERT
        // Check 1: Did we pass the correct timestamp to the API?
        assertEquals(
            expected = expectedTimestamp,
            actual = fakeApi.lastRequestedTimestamp,
            message = "Repository should request stories from exactly 48 hours ago"
        )

        // Check 2: Did we map the response correctly?
        assertEquals(1, result.size)
        assertEquals("Test Title", result[0].newsItem.title)
        assertEquals(NewsCategory.TOP, result[0].category)
    }
}