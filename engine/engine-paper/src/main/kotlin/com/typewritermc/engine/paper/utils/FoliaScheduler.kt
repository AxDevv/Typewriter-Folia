package com.typewritermc.engine.paper.utils

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers as KotlinDispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.coroutines.CoroutineContext

object FoliaSupported {
    private var _isFolia: Boolean? = null

    val isFolia: Boolean
        get() {
            if (_isFolia == null) {
                _isFolia = try {
                    Class.forName("io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler")
                    true
                } catch (e: ClassNotFoundException) {
                    false
                }
            }
            return _isFolia == true
        }
}

val Dispatchers: FoliaDispatchers
    get() = FoliaDispatchers

object FoliaDispatchers {
    val Sync: CoroutineDispatcher get() = FoliaSyncDispatcher
    val TickedAsync: CoroutineDispatcher get() = FoliaAsyncDispatcher
}

private object FoliaAsyncDispatcher : CoroutineDispatcher(), KoinComponent {
    private val scheduler: AsyncScheduler by lazy { server.asyncScheduler }
    private val isEnabled by inject<Boolean>(named("isEnabled"))

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = isEnabled

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!isEnabled) return
        scheduler.run { block.run() }
    }
}

private object FoliaSyncDispatcher : CoroutineDispatcher(), KoinComponent {
    private val scheduler: GlobalRegionScheduler by lazy { server.globalRegionScheduler }
    private val isEnabled by inject<Boolean>(named("isEnabled"))

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = isEnabled

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!isEnabled) return
        scheduler.run { block.run() }
    }
}
