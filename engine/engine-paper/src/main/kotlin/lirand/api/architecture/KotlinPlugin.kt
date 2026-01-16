package lirand.api.architecture

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.typewritermc.engine.paper.utils.FoliaSupported
import lirand.api.LirandAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

abstract class KotlinPlugin : SuspendingJavaPlugin() {
	private val pluginScope: kotlinx.coroutines.CoroutineScope by lazy {
		if (FoliaSupported.isFolia) {
			FoliaPluginScope()
		} else {
			PaperPluginScope(this)
		}
	}

	final override fun onEnable() {
		try {
			LirandAPI.register(this)
		} catch (_: IllegalStateException) {}

		if (FoliaSupported.isFolia) {
			pluginScope.launch {
				@Suppress("LeakingThis")
				onEnableAsync()
			}
		} else {
			super.onEnable()
		}
	}

	final override fun onDisable() {
		if (FoliaSupported.isFolia) {
			pluginScope.launch {
				@Suppress("LeakingThis")
				onDisableAsync()
			}
			pluginScope.cleanup()
		} else {
			super.onDisable()
		}
	}

	private fun kotlinx.coroutines.CoroutineScope.launch(
		block: suspend kotlinx.coroutines.CoroutineScope.() -> Unit
	): kotlinx.coroutines.Job {
		val scope = this
		return kotlinx.coroutines.launch(
			scope.coroutineContext,
			block = block
		)
	}

	private fun kotlinx.coroutines.CoroutineScope.cleanup() {
		(this as? FoliaPluginScope)?.cleanup()
	}

	private class FoliaPluginScope : kotlinx.coroutines.CoroutineScope, KoinComponent {
		private val job = kotlinx.coroutines.SupervisorJob()
		private val isEnabled by inject<Boolean>(named("isEnabled"))

		override val coroutineContext: kotlin.coroutines.CoroutineContext
			get() = if (isEnabled) {
				com.typewritermc.engine.paper.utils.Dispatchers.Sync + job
			} else {
				job
			}

		fun cleanup() {
			job.cancel()
		}
	}

	private class PaperPluginScope(private val plugin: SuspendingJavaPlugin) : kotlinx.coroutines.CoroutineScope {
		override val coroutineContext: kotlin.coroutines.CoroutineContext
			get() = plugin.coroutineContext
	}
}
