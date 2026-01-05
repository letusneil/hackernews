package com.letusneil.shared

import com.letusneil.shared.di.AppDispatchers
import com.letusneil.shared.di.TestAppDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest {

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    val scope = TestScope(dispatcher)
    val testDispatchers: AppDispatchers = TestAppDispatchers(dispatcher)

    @BeforeTest
    fun setupDispatcher() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }
}
