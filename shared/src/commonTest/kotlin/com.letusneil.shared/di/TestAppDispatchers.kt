package com.letusneil.shared.di

import kotlinx.coroutines.CoroutineDispatcher

class TestAppDispatchers(
    private val testDispatcher: CoroutineDispatcher
) : AppDispatchers {
    override val io: CoroutineDispatcher = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
}