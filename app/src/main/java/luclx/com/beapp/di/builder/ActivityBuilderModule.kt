package luclx.com.beapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import luclx.com.beapp.ui.home.MainActivity

/**
 * Provides the android injection service to activity
 */

@Module
abstract class ActivityBuilderModule {
	@ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
	abstract fun contributeMainActivity(): MainActivity
}