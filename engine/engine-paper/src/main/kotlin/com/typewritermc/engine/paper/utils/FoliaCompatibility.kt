package com.typewritermc.engine.paper.utils

import com.typewritermc.engine.paper.plugin
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import java.util.concurrent.CompletableFuture

object FoliaCompatibility {
    @JvmStatic
    val isFolia: Boolean
        get() {
            return try {
                Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }
}

inline fun <T> runAtLocationForResult(
    location: Location,
    owner: Plugin = plugin,
    crossinline action: () -> T,
): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    if (FoliaCompatibility.isFolia) {
        owner.server.regionScheduler.execute(owner, location) {
            try {
                future.complete(action())
            } catch (e: Exception) {
                future.completeExceptionally(e)
            }
        }
    } else {
        plugin.server.scheduler.runTask(owner, Runnable {
            try {
                future.complete(action())
            } catch (e: Exception) {
                future.completeExceptionally(e)
            }
        })
    }
    return future
}

inline fun runAtLocation(
    location: Location,
    owner: Plugin = plugin,
    crossinline action: () -> Unit,
) {
    if (FoliaCompatibility.isFolia) {
        owner.server.regionScheduler.execute(owner, location) {
            try {
                action()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } else {
        plugin.server.scheduler.runTask(owner, Runnable {
            try {
                action()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }
}

inline fun runAtLocationLater(
    location: Location,
    delayTicks: Long,
    owner: Plugin = plugin,
    crossinline action: () -> Unit,
) {
    if (FoliaCompatibility.isFolia) {
        owner.server.regionScheduler.execute(owner, location) {
            action()
        }
    } else {
        plugin.server.scheduler.runTaskLater(owner, Runnable { action() }, delayTicks)
    }
}

inline fun runAtEntity(
    entity: Entity,
    owner: Plugin = plugin,
    crossinline action: () -> Unit,
) {
    if (FoliaCompatibility.isFolia) {
        entity.scheduler.execute(owner, { action() }, null, 1L)
    } else {
        plugin.server.scheduler.runTask(owner, Runnable { action() })
    }
}

inline fun runAsync(
    owner: Plugin = plugin,
    crossinline action: () -> Unit,
) {
    if (FoliaCompatibility.isFolia) {
        owner.server.asyncScheduler.runNow(owner, { action() })
    } else {
        plugin.server.scheduler.runTaskAsynchronously(owner, Runnable { action() })
    }
}

inline fun runGlobal(
    owner: Plugin = plugin,
    crossinline action: () -> Unit,
) {
    if (FoliaCompatibility.isFolia) {
        owner.server.globalRegionScheduler.execute(owner, { action() })
    } else {
        plugin.server.scheduler.runTask(owner, Runnable { action() })
    }
}
