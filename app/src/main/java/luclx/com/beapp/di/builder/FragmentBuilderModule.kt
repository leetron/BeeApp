package luclx.com.beapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import luclx.com.beapp.ui.add.CardAddFragment
import luclx.com.beapp.ui.detail.CardDetailFragment
import luclx.com.beapp.ui.home.CardListFragment

/**
 * Provides the android injection service to fragment
 */

@Module
abstract class FragmentBuilderModule {
	@ContributesAndroidInjector
	abstract fun contributeCardListFragment(): CardListFragment

	@ContributesAndroidInjector
	abstract fun contributeCardDetailFragment(): CardDetailFragment

	@ContributesAndroidInjector
	abstract fun contributeCardAddFragment(): CardAddFragment
}