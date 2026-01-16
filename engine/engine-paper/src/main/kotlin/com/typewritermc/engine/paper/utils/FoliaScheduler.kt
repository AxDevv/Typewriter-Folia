package com.typewritermc.engine.paper.utils

import kotlinx.coroutines.CoroutineDispatcher

object GameDispatchers {
    val Sync: CoroutineDispatcher get() = Dispatchers.Sync
    val TickedAsync: CoroutineDispatcher get() = Dispatchers.TickedAsync
}
