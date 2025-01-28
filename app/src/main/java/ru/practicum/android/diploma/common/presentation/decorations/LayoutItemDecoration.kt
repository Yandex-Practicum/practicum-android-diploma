package ru.practicum.android.diploma.common.presentation.decorations

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LayoutItemDecoration(
    private val firstItemTopOffSet: Int,
) : RecyclerView.ItemDecoration() {

    private var marginInPx = 0

    fun init(context: Context) {
        marginInPx = (firstItemTopOffSet * context.resources.displayMetrics.density).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter ?: return
        val currentPosition = parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION }
        if (!adapter.isFirstPosition(currentPosition)) return

        with(outRect) {
            top = marginInPx
        }
    }

    private fun RecyclerView.Adapter<*>.isFirstPosition(
        currentPosition: Int?,
    ) = currentPosition == 0
}
