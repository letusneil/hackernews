package com.letusneil.hackernews.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.letusneil.hackernews.ui.theme.HackernewsTheme
import com.letusneil.shared.domain.model.NewsCategory
import com.letusneil.shared.domain.model.NewsFeedItem
import com.letusneil.shared.domain.model.NewsItem
import com.letusneil.shared.presentation.home.HomeUiState
import com.letusneil.shared.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onStoryClick: (NewsFeedItem) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onStoryClick = onStoryClick,
        onRefresh = viewModel::refresh,
        onLoadMore = viewModel::loadMoreStories,
        onClearError = viewModel::clearError,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    onStoryClick: (NewsFeedItem) -> Unit,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    onClearError: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            onClearError()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Hacker News") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    actionColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.stories.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.stories.isEmpty() && !uiState.isLoading && !uiState.isRefreshing -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Failed to load stories",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            TextButton(onClick = onRefresh) {
                                Text("Retry")
                            }
                        }
                    }
                }

                else -> {
                    StoryList(
                        stories = uiState.stories,
                        isLoadingMore = uiState.isLoadingMore,
                        onStoryClick = onStoryClick,
                        onLoadMore = onLoadMore
                    )
                }
            }
        }
    }
}

private val previewStories = listOf(
    NewsFeedItem(
        newsItem = NewsItem(
            id = "1",
            title = "Show HN: I built a tool to automatically optimize React performance",
            url = "https://github.com/example/react-optimizer",
            author = "johndoe",
            points = 256,
            commentCount = 89,
            timeAgo = "3 hours ago"
        ),
        category = NewsCategory.TOP
    ),
    NewsFeedItem(
        newsItem = NewsItem(
            id = "2",
            title = "Why Rust is the future of systems programming",
            url = "https://blog.example.com/rust-future",
            author = "rustacean",
            points = 189,
            commentCount = 124,
            timeAgo = "5 hours ago"
        ),
        category = NewsCategory.TOP
    ),
    NewsFeedItem(
        newsItem = NewsItem(
            id = "3",
            title = "Ask HN: What are the best resources for learning system design?",
            url = null,
            author = "curious_dev",
            points = 142,
            commentCount = 67,
            timeAgo = "7 hours ago"
        ),
        category = NewsCategory.ASK
    )
)

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview() {
    HackernewsTheme {
        HomeContent(
            uiState = HomeUiState(stories = previewStories),
            onStoryClick = {},
            onRefresh = {},
            onLoadMore = {},
            onClearError = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentLoadingPreview() {
    HackernewsTheme {
        HomeContent(
            uiState = HomeUiState(isLoading = true),
            onStoryClick = {},
            onRefresh = {},
            onLoadMore = {},
            onClearError = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentErrorPreview() {
    HackernewsTheme {
        HomeContent(
            uiState = HomeUiState(error = "Network error"),
            onStoryClick = {},
            onRefresh = {},
            onLoadMore = {},
            onClearError = {}
        )
    }
}