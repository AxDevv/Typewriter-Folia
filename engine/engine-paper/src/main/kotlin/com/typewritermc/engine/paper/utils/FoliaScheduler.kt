package com.typewritermc.engine.paper.utils

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.coroutines.CoroutineContext

object GameDispatchers {
    val Sync: CoroutineDispatcher get() = Dispatchers.Sync
    val TickedAsync: CoroutineDispatcher get() = Dispatchers.TickedAsync
}
