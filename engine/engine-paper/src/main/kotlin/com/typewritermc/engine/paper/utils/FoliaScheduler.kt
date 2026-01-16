package com.typewritermc.engine.paper.utils

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.plugin.Plugin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit
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

class FoliaAsyncDispatcher(
    private val plugin: Plugin,
    private val scheduler: AsyncScheduler
) : CoroutineDispatcher(), KoinComponent {
    private val isEnabled by inject<Boolean>(named("isEnabled"))

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        return isEnabled
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!isEnabled) return
        scheduler.runNow(plugin, block::run)
    }
}

class FoliaGlobalRegionDispatcher(
    private val plugin: Plugin,
    private val scheduler: GlobalRegionScheduler
) : CoroutineDispatcher(), KoinComponent {
    private val isEnabled by inject<Boolean>(named("isEnabled"))

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        return isEnabled
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!isEnabled) return
        scheduler.runNow(plugin, block::run)
    }

    fun schedule(delay: Long, unit: TimeUnit, block: Runnable) {
        if (!isEnabled) return
        scheduler.runDelayed(plugin, block::run, delay, unit)
    }

    fun scheduleAtFixedRate(delay: Long, period: Long, unit: TimeUnit, block: Runnable) {
        if (!isEnabled) return
        scheduler.runAtFixedRate(plugin, block::run, delay, period, unit)
    }
}
