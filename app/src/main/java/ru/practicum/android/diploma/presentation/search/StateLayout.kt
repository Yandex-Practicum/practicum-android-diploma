package ru.practicum.android.diploma.presentation.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

class StateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var contentView: View? = null
    private var loadingView: View? = null
    private val errorViews = mutableMapOf<Class<out UiError>, View>()
    private var emptyView: View? = null

    private var currentState: ViewState = ViewState.CONTENT

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            contentView = getChildAt(0)
        }
    }

    fun setLoadingView(layoutResId: Int) {
        loadingView = inflateAndAdd(layoutResId)
    }

    fun setErrorView(errorType: Class<out UiError>, layoutResId: Int) {
        val view = LayoutInflater.from(context).inflate(layoutResId, this, false)
        view.visibility = View.GONE
        addView(view)
        errorViews[errorType] = view
    }

    fun setEmptyView(layoutResId: Int) {
        emptyView = inflateAndAdd(layoutResId)
    }

    fun show(state: ViewState, error: UiError? = null) {
        if (state == currentState) return
        currentState = state
        contentView?.visibility = if (state == ViewState.CONTENT) View.VISIBLE else View.GONE
        loadingView?.visibility = if (state == ViewState.LOADING) View.VISIBLE else View.GONE
        emptyView?.visibility = if (state == ViewState.EMPTY) View.VISIBLE else View.GONE
        errorViews.values.forEach { it.visibility = View.GONE }
        if (state == ViewState.ERROR && error != null) {
            val errorView = errorViews[error::class.java]
            errorView?.visibility = View.VISIBLE
        }
    }

    private fun inflateAndAdd(resId: Int): View {
        val view = LayoutInflater.from(context).inflate(resId, this, false)
        addView(view)
        view.visibility = View.GONE
        return view
    }
}
