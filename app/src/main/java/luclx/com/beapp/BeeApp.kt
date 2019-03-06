package luclx.com.beapp

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import luclx.com.beapp.di.component.AppInjector
import luclx.com.beapp.di.component.DaggerAppComponent
import javax.inject.Inject

class BeeApp : Application(), HasActivityInjector {

	@Inject
	lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

	override fun onCreate() {
		super.onCreate()
		DaggerAppComponent
			.builder()
			.application(this)
			.build()
			.inject(this)

//		AppInjector.init(this)
	}

	// from lib dagger for android support
	override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingInjector
}