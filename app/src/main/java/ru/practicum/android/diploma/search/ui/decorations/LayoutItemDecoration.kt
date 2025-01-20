package ru.practicum.android.diploma.search.ui.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LayoutItemDecoration(
    private val firstItemTopOffSet: Int,
) : RecyclerView.ItemDecoration() {

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
            top = firstItemTopOffSet
        }
    }

    private fun RecyclerView.Adapter<*>.isFirstPosition(
        currentPosition: Int?,
    ) = currentPosition == 0
}
