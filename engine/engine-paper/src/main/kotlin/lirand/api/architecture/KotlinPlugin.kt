package lirand.api.architecture

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.typewritermc.engine.paper.utils.FoliaSupported
import com.typewritermc.engine.paper.utils.GameDispatchers
import lirand.api.LirandAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.coroutines.CoroutineContext

abstract class KotlinPlugin : SuspendingJavaPlugin() {
	private val pluginScope: CoroutineScope by lazy {
		if (FoliaSupported.isFolia) {
			FoliaPluginScope()
		} else {
			PaperPluginScope()
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

	private fun CoroutineScope.cleanup() {
		(this as? PluginScope)?.cleanup()
	}

	private interface PluginScope {
		fun cleanup()
	}

	private class FoliaPluginScope : CoroutineScope, PluginScope, KoinComponent {
		private val job = SupervisorJob()
		private val isEnabled by inject<Boolean>(named("isEnabled"))

		override val coroutineContext: CoroutineContext
			get() = if (isEnabled) {
				GameDispatchers.Sync + job
			} else {
				job
			}

		override fun cleanup() {
			job.cancel()
		}
	}

	private class PaperPluginScope : CoroutineScope, PluginScope {
		private val job = SupervisorJob()

		override val coroutineContext: CoroutineContext
			get() = GameDispatchers.Sync + job

		override fun cleanup() {
			job.cancel()
		}
	}
}
