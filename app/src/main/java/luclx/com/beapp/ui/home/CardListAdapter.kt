package luclx.com.beapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import luclx.com.beapp.R
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.databinding.ItemCardBinding
import luclx.com.beapp.ui.adapter.BaseAdapter
import luclx.com.beapp.ui.home.CardListAdapter.CardViewHolder
import luclx.com.beapp.utils.LogUtils

class CardListAdapter(
    private val listener: ItemClickListener
) : BaseAdapter<CardViewHolder, CardEntity>(), Filterable {

    private var cards: MutableList<CardEntity> = mutableListOf()

    private var cardsFiltered: MutableList<CardEntity> = mutableListOf()

    fun addLocal(card: CardEntity) {
        this.cards.add(0, card)
        this.cardsFiltered.add(0, card)
        notifyItemChanged(0)
        notifyItemInserted(0)
    }

    override fun setData(data: List<CardEntity>) {
        LogUtils.v("BEEAPP", "ADD MORE DATA" + data.size)
        this.cards.addAll(data)
        this.cardsFiltered.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder.create(LayoutInflater.from(parent.context), parent, listener)

    override fun getItemCount(): Int {
        LogUtils.v("BEEAPP", "SIZE " + this.cardsFiltered.size)
        return this.cardsFiltered.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(holder.itemView.context, cardsFiltered[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                cardsFiltered = if (charString.isEmpty()) {
                    cards
                } else {
                    val filteredList = mutableListOf<CardEntity>()
                    cards.forEach {
                        // name and phone match condition
                        if (it.getFullName().toLowerCase().contains(charString.toLowerCase())
                            || it.mobile?.contains(charString.toLowerCase()) == true
                        ) {
                            filteredList.add(it)
                        }
                    }
                    filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = cardsFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                cardsFiltered = filterResults.values as MutableList<CardEntity>
                notifyDataSetChanged()
            }
        }
    }

    class CardViewHolder(
        private val binding: ItemCardBinding,
        listener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private var options: RequestOptions

        companion object {
            fun create(
                inflater: LayoutInflater,
                parent: ViewGroup,
                listener: ItemClickListener
            ): CardViewHolder {
                val itemCardListBinding = ItemCardBinding.inflate(inflater, parent, false)
                return CardViewHolder(itemCardListBinding, listener)
            }
        }

        init {
            binding.root.setOnClickListener { listener.onCardClick(binding.card!!) }
            options = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.viewholder)
                .error(R.mipmap.viewholder)
        }

        fun onBind(context: Context, cardEntity: CardEntity) {
            binding.card = cardEntity
            Glide.with(context).load(cardEntity.avatar).apply(options).into(binding.imageView)
            binding.executePendingBindings()
        }
    }
}