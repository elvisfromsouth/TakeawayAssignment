package com.takeaway.borzikov.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Wrapping above default coroutines Dispatchers,
 * for easily working in tests.
 */
interface AppDispatchers {

    /**
     * According to @see[Dispatchers.IO]
     */
    fun io(): CoroutineDispatcher

    /**
     * According to @see[Dispatchers.Unconfined]
     */
    fun unconfined(): CoroutineDispatcher

    /**
     * According to @see[Dispatchers.Default]
     */
    fun default(): CoroutineDispatcher

    /**
     * According to @see[Dispatchers.Main]
     */
    fun main(): CoroutineDispatcher
}

class DefaultAppDispatchers : AppDispatchers {
    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun unconfined(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}

