package com.letusneil.hackernews.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.letusneil.hackernews.ui.components.LiquidGlassBackground
import com.letusneil.hackernews.ui.components.LiquidGlassGradient
import com.letusneil.hackernews.ui.theme.HackernewsTheme
import com.letusneil.shared.domain.model.StoryCategory
import com.letusneil.shared.domain.model.StoriesFeedItem
import com.letusneil.shared.domain.model.StoryItem
import com.letusneil.shared.presentation.home.HomeUiState
import com.letusneil.shared.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onStoryClick: (StoriesFeedItem) -> Unit,
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
    onStoryClick: (StoriesFeedItem) -> Unit,
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LiquidGlassGradient)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Glass App Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.15f),
                                Color.White.copy(alpha = 0.05f)
                            )
                        )
                    )
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Hacker News",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Content
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    uiState.isLoading && uiState.stories.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
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
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 16.sp
                                )
                                TextButton(onClick = onRefresh) {
                                    Text(
                                        text = "Retry",
                                        color = Color(0xFF64B5F6)
                                    )
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

        // Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = Color.White.copy(alpha = 0.9f),
                contentColor = Color.Black
            )
        }
    }
}

private val previewStories = listOf(
    StoriesFeedItem(
        storyItem = StoryItem(
            id = "1",
            title = "Show HN: I built a tool to automatically optimize React performance",
            url = "https://github.com/example/react-optimizer",
            author = "johndoe",
            points = 256,
            commentCount = 89,
            timeAgo = "3 hours ago"
        ),
        category = StoryCategory.TOP
    ),
    StoriesFeedItem(
        storyItem = StoryItem(
            id = "2",
            title = "Why Rust is the future of systems programming",
            url = "https://blog.example.com/rust-future",
            author = "rustacean",
            points = 189,
            commentCount = 124,
            timeAgo = "5 hours ago"
        ),
        category = StoryCategory.TOP
    ),
    StoriesFeedItem(
        storyItem = StoryItem(
            id = "3",
            title = "Ask HN: What are the best resources for learning system design?",
            url = null,
            author = "curious_dev",
            points = 142,
            commentCount = 67,
            timeAgo = "7 hours ago"
        ),
        category = StoryCategory.ASK
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