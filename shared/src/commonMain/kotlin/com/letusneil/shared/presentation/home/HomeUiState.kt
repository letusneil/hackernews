package com.letusneil.shared.presentation.home

import com.letusneil.shared.domain.model.NewsFeedItem

data class HomeUiState(
    val stories: List<NewsFeedItem> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val canLoadMore: Boolean = true
)