package luclx.com.beapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_card_detail.*
import luclx.com.beapp.R
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.databinding.FragmentCardDetailBinding
import luclx.com.beapp.ui.base.BaseFragment
import luclx.com.beapp.ui.home.CardViewModel
import luclx.com.beapp.utils.Constant
import luclx.com.beapp.utils.spanWith

class CardDetailFragment : BaseFragment<CardViewModel, FragmentCardDetailBinding>(),
    View.OnClickListener {

    /**
     * Provide new instance
     */
    companion object {
        fun newInstance(cardEntity: CardEntity): CardDetailFragment {
            val fragment = CardDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constant.KEYS_CARD, cardEntity)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getViewModel(): Class<CardViewModel> = CardViewModel::class.java

    override fun getLayoutRes(): Int = R.layout.fragment_card_detail

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClick(this, btnBack)
        arguments?.let {
            val card = it.getParcelable<CardEntity>(Constant.KEYS_CARD)
            card?.let {
                dataBinding.card = card

                val about = "About: " + (card.about ?: "N/A")
                val spannedText = SpannableString(about)
                spannedText.spanWith("About: ") {
                    what = ForegroundColorSpan(Color.BLACK)
                    flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                }
                dataBinding.tvAbout.text = spannedText

                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.viewholder)
                    .error(R.mipmap.viewholder)

                Glide.with(dataBinding.imageView.context).load(card.avatar).apply(options)
                    .into(dataBinding.imageView)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            btnBack.id -> {
                activity?.let {
                    it.onBackPressed()
                }
            }
        }
    }
}