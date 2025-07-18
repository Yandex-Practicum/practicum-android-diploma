package ru.practicum.android.diploma.ui.common

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class PaddingItemDecoration(
    @Px private val orientedAxisPadding: Int,
    @Px private val startEdgeOrientedAxisPadding: Int,
    @Px private val endEdgeOrientedAxisPadding: Int,
    @Px private val orthoAxisPadding: Int
) : RecyclerView.ItemDecoration() {
    constructor(
        @Px orientedAxisPadding: Int,
        @Px edgeOrientedAxisPadding: Int,
        @Px orthoAxisPadding: Int
    ) : this(
        orientedAxisPadding,
        edgeOrientedAxisPadding,
        edgeOrientedAxisPadding,
        orthoAxisPadding
    )

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.setEmpty()

        val orientation = (parent.layoutManager as? LinearLayoutManager)?.orientation
        val itemCount = parent.adapter?.itemCount
        val adapterPosition = parent.getChildAdapterPosition(view)

        val startPadding = if (adapterPosition == 0) startEdgeOrientedAxisPadding else orientedAxisPadding
        val endPadding = if (adapterPosition == (itemCount ?: 0) - 1) {
            endEdgeOrientedAxisPadding
        } else {
            orientedAxisPadding
        }

        when (orientation) {
            RecyclerView.VERTICAL -> outRect.set(orthoAxisPadding, startPadding, orthoAxisPadding, endPadding)
            RecyclerView.HORIZONTAL -> outRect.set(startPadding, orthoAxisPadding, endPadding, orthoAxisPadding)
        }
    }
}
