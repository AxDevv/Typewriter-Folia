package com.typewritermc.engine.paper.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job

@Deprecated("Obsolete approach for kotlin coroutines", level = DeprecationLevel.ERROR)
enum class ThreadType {
    @Deprecated(
        "Obsolete approach for kotlin coroutines",
        ReplaceWith(
            "GameDispatchers.Sync",
            "com.typewritermc.engine.paper.utils.GameDispatchers"
        ),
        level = DeprecationLevel.ERROR
    )
    SYNC,

    @Deprecated(
        "Obsolete approach for kotlin coroutines",
        ReplaceWith(
            "GameDispatchers.TickedAsync",
            "com.typewritermc.engine.paper.utils.GameDispatchers"
        ),
        level = DeprecationLevel.ERROR
    )
    ASYNC,

    @Deprecated(
        "Obsolete approach for kotlin coroutines",
        ReplaceWith(
            "Dispatchers.UntickedAsync",
            "kotlinx.coroutines.Dispatchers",
            "com.typewritermc.core.utils.UnTickedAsync"
        ),
        level = DeprecationLevel.ERROR
    )
    DISPATCHERS_ASYNC,

    @Deprecated(
        "Obsolete approach for kotlin coroutines",
        ReplaceWith(
            "Dispatchers.Unconfined",
            "kotlinx.coroutines.Dispatchers",
        ),
        level = DeprecationLevel.ERROR
    )
    REMAIN,
    ;

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> switchContext(block: suspend () -> T): T {
        throw UnsupportedOperationException()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun launch(block: suspend () -> Unit): Job {
        throw UnsupportedOperationException()
    }
}