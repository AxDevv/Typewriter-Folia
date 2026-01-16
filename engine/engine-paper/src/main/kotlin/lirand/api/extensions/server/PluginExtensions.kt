package lirand.api.extensions.server

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import com.typewritermc.engine.paper.utils.FoliaSupported
import org.bukkit.NamespacedKey
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

fun Plugin.registerEvents(
	vararg listeners: Listener
) = listeners.forEach { server.pluginManager.registerEvents(it, this) }

fun Plugin.registerSuspendingEvents(
	vararg listeners: Listener
) {
	if (FoliaSupported.isFolia) {
		listeners.forEach { server.pluginManager.registerEvents(it, this) }
	} else {
		listeners.forEach { server.pluginManager.registerSuspendingEvents(it, this) }
	}
}

fun Plugin.registerSuspendingEvents(
	listener: Listener
) {
	if (FoliaSupported.isFolia) {
		server.pluginManager.registerEvents(listener, this)
	} else {
		server.pluginManager.registerSuspendingEvents(listener, this)
	}
}

fun PluginManager.registerSuspendingEvents(
	listener: Listener,
	plugin: Plugin
) {
	if (FoliaSupported.isFolia) {
		registerEvents(listener, plugin)
	} else {
		registerSuspendingEvents(listener, plugin)
	}
}