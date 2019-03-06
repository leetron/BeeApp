package luclx.com.beapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * This is magic adapter for recyclerview
 * Made recyclerview is easy to implement
 * Please refer Extension.kt -> RecyclerView.setUp
 * BEST ADAPTER EVER
 */
abstract class AbstractAdapter<in ITEM> constructor(
	private var itemList: MutableList<ITEM>,
	private val layoutResId: Int
) : RecyclerView.Adapter<AbstractAdapter.Holder>() {

	protected abstract fun onItemClick(itemView: View, position: Int)

	protected abstract fun View.bind(item: ITEM)

	override fun getItemCount() = itemList.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		val viewHolder =
			Holder(
				LayoutInflater.from(parent.context).inflate(
					layoutResId,
					parent,
					false
				)
			)
		val itemView = viewHolder.itemView
		itemView.setOnClickListener {
			val position = viewHolder.adapterPosition
			if (position != RecyclerView.NO_POSITION) {
				onItemClick(itemView, position)
			}
		}
		return viewHolder
	}

	override fun onBindViewHolder(holder: Holder, position: Int) {
		val item = itemList[position]
		holder.itemView.bind(item)
	}

	fun update(items: List<ITEM>) {
		DiffUtil.calculateDiff(DiffUtilCallback(itemList, items)).dispatchUpdatesTo(this)
	}

	fun add(item: ITEM) {
		itemList.add(item)
		notifyItemInserted(itemList.size)
	}

	fun remove(position: Int) {
		itemList.removeAt(position)
		notifyItemRemoved(position)
	}

	fun set(items: List<ITEM>?) {
		itemList.clear()
		items?.let {
			itemList.addAll(it)
		}
		notifyDataSetChanged()
	}

	class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}