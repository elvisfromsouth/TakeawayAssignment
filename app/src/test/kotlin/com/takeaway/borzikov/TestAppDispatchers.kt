package com.takeaway.borzikov

import com.takeaway.borzikov.common.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher


class TestAppDispatchers(
    private val testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
) : AppDispatchers {


    override fun io(): CoroutineDispatcher {
        return testDispatcher
    }

    override fun unconfined(): CoroutineDispatcher {
        return testDispatcher
    }

    override fun default(): CoroutineDispatcher {
        return testDispatcher
    }

    override fun main(): CoroutineDispatcher {
        return testDispatcher
    }
}