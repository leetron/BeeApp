package luclx.com.beapp.ui.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import luclx.com.beapp.data.remote.Resource

/**
 * Create function for binding adapter
 */

object BindingAdapter {
	@JvmStatic
	@BindingAdapter("visibleGone")
	fun showHide(view: View, show: Boolean) {
		view.visibility = if (show) View.VISIBLE else View.GONE
	}

	@JvmStatic
	@BindingAdapter("resource")
	fun <D> setResource(
		recyclerView: RecyclerView,
		resource: Resource<D>?
	) {
		resource?.data?.let {
			val adapter = recyclerView.adapter
			adapter?.let {
				(it as BaseAdapter<*, D>).setData(resource.data as List<D>)
			}
		}
	}
}