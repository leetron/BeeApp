package luclx.com.beapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, D> : RecyclerView.Adapter<VH>() {
    abstract fun setData(data: List<D>)
}