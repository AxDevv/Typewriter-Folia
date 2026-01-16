package com.typewritermc.engine.paper.utils

import com.github.shynixn.mccoroutine.bukkit.asyncDispatcher
import com.github.shynixn.mccoroutine.bukkit.minecraftDispatcher
import com.typewritermc.core.utils.TypewriterDispatcher
import com.typewritermc.engine.paper.plugin
import kotlinx.coroutines.CoroutineDispatcher

private object PaperTickedAsyncDispatcher : TypewriterDispatcher(plugin.asyncDispatcher)
private object PaperSyncDispatcher : TypewriterDispatcher(plugin.minecraftDispatcher)

val PaperDispatchers.Sync: CoroutineDispatcher get() = PaperSyncDispatcher
val PaperDispatchers.TickedAsync: CoroutineDispatcher get() = PaperTickedAsyncDispatcher
