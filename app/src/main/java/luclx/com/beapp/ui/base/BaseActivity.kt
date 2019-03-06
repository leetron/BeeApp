package luclx.com.beapp.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * https://medium.com/@serapbercin001/how-to-use-android-injector-for-activity-and-fragment-objects-through-new-dagger-2-with-kotlin-704eb8a98c43
 */

abstract class BaseActivity<D : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {

	@Inject
	lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

	private lateinit var dataBinding: D

	@LayoutRes
	abstract fun getLayoutResource(): Int

	override fun onCreate(savedInstanceState: Bundle?) {
		AndroidInjection.inject(this)
		super.onCreate(savedInstanceState)
		dataBinding = DataBindingUtil.setContentView(this, getLayoutResource())
	}

	override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}