package luclx.com.beapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil

internal class DiffUtilCallback<ITEM>(
	private val oldItems: List<ITEM>,
	private val newItems: List<ITEM>
) : DiffUtil.Callback() {

	override fun getOldListSize() = oldItems.size

	override fun getNewListSize() = newItems.size

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
		oldItems[oldItemPosition] == newItems[newItemPosition]

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
		oldItems[oldItemPosition] == newItems[newItemPosition]
}