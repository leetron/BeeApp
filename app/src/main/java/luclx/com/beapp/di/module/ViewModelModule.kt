package luclx.com.beapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import luclx.com.beapp.ui.base.ViewModelFactory
import luclx.com.beapp.ui.home.CardViewModel

/**
 * Allow us to inject dependencies via constructor injection
 **/

@Module
abstract class ViewModelModule {
	@Binds
	@IntoMap
	@ViewModelKey(CardViewModel::class)
	abstract fun bindsCardViewModel(cardViewModel: CardViewModel): ViewModel

	@Binds
	abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}