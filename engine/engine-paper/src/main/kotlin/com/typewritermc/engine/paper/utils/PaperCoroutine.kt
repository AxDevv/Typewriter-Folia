package com.typewritermc.engine.paper.utils

import com.github.shynixn.mccoroutine.bukkit.asyncDispatcher
import com.github.shynixn.mccoroutine.bukkit.minecraftDispatcher
import com.typewritermc.core.utils.TypewriterDispatcher
import com.typewritermc.engine.paper.plugin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

private object PaperTickedAsyncDispatcher : TypewriterDispatcher(plugin.asyncDispatcher)
private object PaperSyncDispatcher : TypewriterDispatcher(plugin.minecraftDispatcher)

private object FoliaTickedAsyncDispatcher : FoliaAsyncDispatcher(server.asyncScheduler)
private object FoliaSyncDispatcher : FoliaGlobalRegionDispatcher(server.globalRegionScheduler)

val Dispatchers.Sync: CoroutineDispatcher
    get() = if (FoliaSupported.isFolia) FoliaSyncDispatcher else PaperSyncDispatcher

val Dispatchers.TickedAsync: CoroutineDispatcher
    get() = if (FoliaSupported.isFolia) FoliaTickedAsyncDispatcher else PaperTickedAsyncDispatcher

