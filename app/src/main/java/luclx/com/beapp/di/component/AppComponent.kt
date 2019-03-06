package luclx.com.beapp.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import luclx.com.beapp.BeeApp
import luclx.com.beapp.di.builder.ActivityBuilderModule
import luclx.com.beapp.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		AndroidInjectionModule::class
		, AndroidSupportInjectionModule::class
		, AppModule::class
		, ActivityBuilderModule::class
	]
)
interface AppComponent {

	@Component.Builder
	interface Builder {

		@BindsInstance
		fun application(application: Application): Builder

		fun build(): AppComponent
	}

	fun inject(beeApp: BeeApp)
}