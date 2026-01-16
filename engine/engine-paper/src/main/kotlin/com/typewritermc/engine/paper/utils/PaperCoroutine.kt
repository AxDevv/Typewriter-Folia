package com.typewritermc.engine.paper.utils

import com.github.shynixn.mccoroutine.bukkit.asyncDispatcher
import com.github.shynixn.mccoroutine.bukkit.minecraftDispatcher
import com.typewritermc.core.utils.TypewriterDispatcher
import com.typewritermc.engine.paper.plugin
import kotlinx.coroutines.CoroutineDispatcher

private object PaperTickedAsyncDispatcher : TypewriterDispatcher(plugin.asyncDispatcher)
private object PaperSyncDispatcher : TypewriterDispatcher(plugin.minecraftDispatcher)

object PaperDispatchers {
    val Sync: CoroutineDispatcher get() = PaperSyncDispatcher
    val TickedAsync: CoroutineDispatcher get() = PaperTickedAsyncDispatcher
}
