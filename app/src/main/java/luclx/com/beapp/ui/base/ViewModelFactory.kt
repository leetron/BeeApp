package luclx.com.beapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * https://medium.com/@marco_cattaneo/android-viewmodel-and-factoryprovider-good-way-to-manage-it-with-dagger-2-d9e20a07084c
 */

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(
	private val creators: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val creator = creators[modelClass] ?: creators.entries.firstOrNull {
			modelClass.isAssignableFrom(it.key)
		}?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
		try {
			return creator.get() as T
		} catch (e: Exception) {
			throw RuntimeException(e)
		}
	}
}