package com.letusneil.shared.data.repository

import com.letusneil.shared.BaseTest
import com.letusneil.shared.data.remote.fakes.FakeHackerNewsApi
import com.letusneil.shared.domain.model.NewsCategory
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Instant

class NewsRepositoryTest : BaseTest() {

    private val fixedNow = Instant.parse("2025-01-03T12:00:00Z")
    private val expectedTimestamp = Instant.parse("2025-01-01T12:00:00Z").epochSeconds
    private val fixedClock = object : Clock {
        override fun now(): Instant = fixedNow
    }

    private lateinit var fakeApi: FakeHackerNewsApi
    private lateinit var repository: NewsRepositoryImpl

    @BeforeTest
    fun setup() {
        fakeApi = FakeHackerNewsApi()
        repository = NewsRepositoryImpl(
            api = fakeApi,
            dispatchers = testDispatchers,
            clock = fixedClock
        )
    }

    @Test
    fun `getTopStories calculates correct timestamp and maps response`() = scope.runTest {
        // ACT
        val result = repository.getTopStories(page = 1)

        // ASSERT
        assertEquals(
            expected = expectedTimestamp,
            actual = fakeApi.lastRequestedTimestamp,
            message = "Repository should request stories from exactly 48 hours ago"
        )
        assertEquals(1, result.size)
        assertEquals("Test Title", result[0].newsItem.title)
        assertEquals(NewsCategory.TOP, result[0].category)
    }

    @Test
    fun `getTopStories passes page parameter to API`() = scope.runTest {
        // ACT
        repository.getTopStories(page = 5)

        // ASSERT
        assertEquals(5, fakeApi.lastRequestedPage)
    }

    @Test
    fun `getTopStories propagates API errors`() = scope.runTest {
        // ARRANGE
        fakeApi.shouldThrowError = true

        // ACT & ASSERT
        val exception = assertFailsWith<Exception> {
            repository.getTopStories(page = 0)
        }
        assertEquals("Fake API Error", exception.message)
    }

    @Test
    fun `getTopStories returns empty list when API returns no hits`() = scope.runTest {
        // ARRANGE
        fakeApi.responseHits = emptyList()

        // ACT
        val result = repository.getTopStories(page = 0)

        // ASSERT
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getTopStories maps all fields correctly`() = scope.runTest {
        // ACT
        val result = repository.getTopStories(page = 0)

        // ASSERT
        val newsItem = result[0].newsItem
        assertEquals("test_id", newsItem.id)
        assertEquals("Test Title", newsItem.title)
        assertEquals(21, newsItem.points)
        assertEquals(42, newsItem.commentCount)
    }
}