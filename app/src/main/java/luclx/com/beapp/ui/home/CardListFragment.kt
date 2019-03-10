package luclx.com.beapp.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_card_list.fab
import luclx.com.beapp.R
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.data.remote.Status
import luclx.com.beapp.databinding.FragmentCardListBinding
import luclx.com.beapp.event.OnAddNewCard
import luclx.com.beapp.ui.add.CardAddFragment
import luclx.com.beapp.ui.base.BaseFragment
import luclx.com.beapp.ui.detail.CardDetailFragment
import luclx.com.beapp.utils.Constant
import luclx.com.beapp.utils.FragmentUtils
import luclx.com.beapp.utils.LogUtils
import luclx.com.beapp.utils.checkReachedLastItem
import org.greenrobot.eventbus.Subscribe

class CardListFragment :
	BaseFragment<CardViewModel, FragmentCardListBinding>(),
	ItemClickListener,
	View.OnClickListener {

	companion object {
		fun newInstance() = CardListFragment()
	}

	override fun getViewModel(): Class<CardViewModel> = CardViewModel::class.java

	override fun getLayoutRes(): Int = R.layout.fragment_card_list

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initSearch()
		initCardList()
		initNotifyData()
		setOnClick(this, dataBinding.fab)
		getData(++viewModel.page, true)
	}

	override fun onCardClick(cardEntity: CardEntity) {
		activity?.let {
			val fragmentDetailFragment = CardDetailFragment.newInstance(cardEntity)
			FragmentUtils.replaceFragment(
				it as AppCompatActivity,
				fragmentDetailFragment,
				R.id.container,
				true,
				Constant.TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT
			)
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			fab.id -> {
				val fragmentAddFragment = CardAddFragment.newInstance()
				FragmentUtils.replaceFragment(
					activity as AppCompatActivity,
					fragmentAddFragment,
					R.id.container,
					true,
					Constant.TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT
				)
			}
		}
	}

	private fun initSearch() {
		activity?.let {
			val searchManager = it.getSystemService(Context.SEARCH_SERVICE) as SearchManager
			val searchView = dataBinding.searchView

			searchView.setSearchableInfo(
				searchManager.getSearchableInfo(it.componentName)
			)
			searchView.maxWidth = Integer.MAX_VALUE

			searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String): Boolean {
					dataBinding.rvCards.adapter?.let { adapter ->
						(adapter as CardListAdapter).filter.filter(query)
					}
					return false
				}

				override fun onQueryTextChange(query: String): Boolean {
					dataBinding.rvCards.adapter?.let { adapter ->
						(adapter as CardListAdapter).filter.filter(query)
					}
					return false
				}
			})
		}
	}

	private fun initCardList() {
		dataBinding.rvCards.layoutManager = LinearLayoutManager(activity)
		dataBinding.rvCards.adapter = CardListAdapter(this)
		dataBinding.rvCards.checkReachedLastItem {
			// if we have reach the end to the recyclerView
			if (this && viewModel.canLoad && !viewModel.loading) {
				getData(++viewModel.page)
			}
		}
	}

	private fun getData(page: Int, needLocal: Boolean = false) {
		viewModel.getCards(page, needLocal).observe(this, Observer {
			when (it.status) {
				Status.LOADING -> {
					LogUtils.v("BEEAPP", "LOADING")
					dataBinding.progress.visibility = View.VISIBLE
					viewModel.loading = true
				}
				Status.ERROR -> {
					LogUtils.v("BEEAPP", "ERROR")
					dataBinding.progress.visibility = View.GONE
					viewModel.loading = true
					activity?.let { ac ->
						Toast.makeText(ac, it.message, Toast.LENGTH_SHORT).show()
					}
				}
				Status.SUCCESS -> {
					LogUtils.v("BEEAPP", "SUCCESS")
					dataBinding.progress.visibility = View.GONE
					viewModel.loading = false
					viewModel.canLoad = it.data != null && it.data.size >= 50
				}
			}
			dataBinding.resource = it
		})
	}

	@Subscribe
	fun updateAddCard(newCardEvent: OnAddNewCard) {
//		(dataBinding.rvCards.adapter as CardListAdapter).addLocal(newCardEvent.card)
//		dataBinding.rvCards.smoothScrollToPosition(0)
	}

	private fun initNotifyData() {
		Handler().postDelayed({
			viewModel.notifyData().observe(this, Observer { card ->
				card?.let {
					(dataBinding.rvCards.adapter as CardListAdapter).addLocal(it)
					dataBinding.rvCards.smoothScrollToPosition(0)
				}
			})
		}, 1000)
	}
}