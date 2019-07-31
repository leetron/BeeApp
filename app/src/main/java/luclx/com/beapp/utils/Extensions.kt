package luclx.com.beapp.utils

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.reactivex.subjects.PublishSubject
import luclx.com.beapp.BuildConfig
import luclx.com.beapp.ui.adapter.BaseSimpleAdapter
import java.util.concurrent.TimeUnit

fun <ITEM> RecyclerView.setUp(
    items: MutableList<ITEM>,
    layoutResId: Int,
    bindHolder: View.(ITEM) -> Unit,
    itemClick: ITEM.() -> Unit,
    itemPositionClick: Int.() -> Unit,
    manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context),
    divider: RecyclerView.ItemDecoration? = null
): BaseSimpleAdapter<ITEM> {
    layoutManager = manager
    if (divider != null) {
        addItemDecoration(divider)
    }
    return BaseSimpleAdapter(
        items,
        layoutResId,
        bindHolder,
        itemClick,
        itemPositionClick
    ).apply { adapter = this }
}

fun RecyclerView.checkReachedLastItem(callback: Boolean.() -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lm = (recyclerView.layoutManager as LinearLayoutManager)
            val visibleItemCount = lm.childCount
            val totalItemCount = lm.itemCount
            val firstVisibleItemPosition = lm.findFirstVisibleItemPosition()

            // if we have reach the end to the recyclerView
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                callback(true)
            } else {
                callback(false)
            }
        }
    })
}

fun SpannableString.spanWith(target: String, apply: SpannableBuilder.() -> Unit) {
    val builder = SpannableBuilder()
    apply(builder)

    val start = this.indexOf(target)
    val end = start + target.length

    setSpan(builder.what, start, end, builder.flags)
}

class SpannableBuilder {
    lateinit var what: Any
    var flags: Int = 0
}

/**
 * create deep copy
 */
fun <T : Any> T.clone(): T {
    val stringSource = Gson().toJson(this, this::class.java)
    return Gson().fromJson<T>(stringSource, this::class.java)
}

/**
 * Execute in main thread
 */
inline fun uiThreadExecutor(crossinline block: () -> Unit) {
    val mainHandler = Handler(Looper.getMainLooper())
    mainHandler.post {
        block()
    }
}

inline fun debugMode(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

@SuppressLint("CheckResult")
fun EditText.addTextChangeFunction(
    timeDebounce: Long = 1000,
    minLength: Int = 0,
    callback: (String) -> Unit
) {
    val subject = PublishSubject.create<String>()
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(e: Editable?) {
            subject.onNext(e.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })
    subject
        .debounce(timeDebounce, TimeUnit.MILLISECONDS)
        .filter { s ->
            s.length >= minLength
        }
        .compose(RxUtil.applyObservableSchedulers())
        .subscribe { s ->
            callback(s)
        }
}
