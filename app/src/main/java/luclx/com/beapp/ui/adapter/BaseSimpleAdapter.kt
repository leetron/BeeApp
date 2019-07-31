package luclx.com.beapp.ui.adapter

import android.view.View

class BaseSimpleAdapter<in ITEM>(
    private var items: MutableList<ITEM>,
    layoutResId: Int,
    private val bindHolder: View.(ITEM) -> Unit,
    private val itemClick: ITEM.() -> Unit,
    private val itemPositionClick: Int.() -> Unit
) : AbstractAdapter<ITEM>(items, layoutResId) {

    override fun onItemClick(itemView: View, position: Int) {
        items[position].itemClick()
        itemPositionClick(position)
    }

    override fun View.bind(item: ITEM) {
        bindHolder(item)
    }
}
