package luclx.com.beapp.ui.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.fragment_card_add.btnClose
import kotlinx.android.synthetic.main.fragment_card_add.btnSave
import kotlinx.android.synthetic.main.fragment_card_add.etAbout
import kotlinx.android.synthetic.main.fragment_card_add.etAddress
import kotlinx.android.synthetic.main.fragment_card_add.etDOB
import kotlinx.android.synthetic.main.fragment_card_add.etGender
import kotlinx.android.synthetic.main.fragment_card_add.etName
import kotlinx.android.synthetic.main.fragment_card_add.etPosition
import luclx.com.beapp.R
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.databinding.FragmentCardAddBinding
import luclx.com.beapp.event.OnAddNewCard
import luclx.com.beapp.ui.base.BaseFragment
import luclx.com.beapp.ui.home.CardViewModel
import luclx.com.beapp.utils.RxUtil
import org.greenrobot.eventbus.EventBus

class CardAddFragment : BaseFragment<CardViewModel, FragmentCardAddBinding>(),
	TextWatcher,
	View.OnClickListener {
	/**
	 * Provide new instance
	 */
	companion object {
		fun newInstance() = CardAddFragment()
	}

	override fun getViewModel(): Class<CardViewModel> = CardViewModel::class.java

	override fun getLayoutRes(): Int = R.layout.fragment_card_add

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		setOnClick(this, btnClose, btnSave)
		etName.addTextChangedListener(this)
		etAddress.addTextChangedListener(this)
		etPosition.addTextChangedListener(this)
		etGender.addTextChangedListener(this)
		setStatus(false)
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			btnClose.id -> {
				activity?.let {
					it.onBackPressed()
				}
			}
			btnSave.id -> {
				val newCard = createCard()
				viewModel.addCard(newCard)
					.compose(RxUtil.applyCompletableSchedulers())
					.subscribe {
						btnClose.performClick()
						EventBus.getDefault().post(
							OnAddNewCard(newCard)
						)
					}
			}
		}
	}

	override fun afterTextChanged(s: Editable?) {
		setStatus(
			etName.text.isNotEmpty() &&
				etAddress.text.isNotEmpty() &&
				etPosition.text.isNotEmpty() &&
				etGender.text.isNotEmpty()
		)
	}

	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
	}

	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
	}

	private fun setStatus(enable: Boolean) {
		btnSave.isEnabled = enable
		btnSave.alpha = if (enable) 1.0f else 0.25f
	}

	private fun createCard() = CardEntity(
		first_name = etName.text.toString(),
		address = etAddress.text.toString(),
		position = etPosition.text.toString(),
		gender = etGender.text.toString(),
		about = etAbout.text.toString(),
		dob = etDOB.text.toString(),
		avatar = null,
		company = null,
		email = null,
		last_name = null,
		mobile = null,
		id = (100..100000).random(),
		isMind = true
	)
}