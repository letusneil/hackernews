package com.letusneil.shared.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letusneil.shared.domain.usecase.GetTopStoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTopStoriesUseCase: GetTopStoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadStories()
    }

    fun loadStories() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getTopStoriesUseCase(page = 0)
                .onSuccess { stories ->
                    _uiState.update {
                        it.copy(
                            stories = stories,
                            isLoading = false,
                            currentPage = 0,
                            canLoadMore = stories.isNotEmpty()
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load stories"
                        )
                    }
                }
        }
    }

    fun loadMoreStories() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.canLoadMore) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }

            val nextPage = currentState.currentPage + 1
            getTopStoriesUseCase(page = nextPage)
                .onSuccess { newStories ->
                    _uiState.update {
                        it.copy(
                            stories = it.stories + newStories,
                            isLoadingMore = false,
                            currentPage = nextPage,
                            canLoadMore = newStories.isNotEmpty()
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoadingMore = false,
                            error = error.message ?: "Failed to load more stories"
                        )
                    }
                }
        }
    }

    fun refresh() {
        if (_uiState.value.isRefreshing) return

        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, error = null) }

            getTopStoriesUseCase(page = 0)
                .onSuccess { stories ->
                    _uiState.update {
                        it.copy(
                            stories = stories,
                            isRefreshing = false,
                            currentPage = 0,
                            canLoadMore = stories.isNotEmpty()
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            error = error.message ?: "Failed to refresh stories"
                        )
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}