package ru.practicum.android.diploma.search.ui.fragment.adapter_delegate

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.models.Vacancy
import kotlin.math.abs

class RecyclerViewSwipeCallback(
    private val context: Context,
    private val onDeleteSwipe: (Vacancy) -> Unit,
) : ItemTouchHelper.Callback() {

    private var coefficientRight = 0.01F
    private val rightContainerColor = context.getColor(R.color.red)
    private var deleteIcon: Drawable? = null

    var list = listOf<Vacancy>()

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ) = makeMovementFlags(0, ItemTouchHelper.LEFT)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = true

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float = 0.1f

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        @Suppress("DEPRECATION")
        if (direction == ItemTouchHelper.LEFT) {
            onDeleteSwipe(list[viewHolder.adapterPosition])
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val paint = Paint()

            val marginHorizontal = 16.toPx
            val vhHeight = viewHolder.itemView.bottom - viewHolder.itemView.top
            val vhWidth = viewHolder.itemView.width

            if (dX < 30f) {

                coefficientRight = abs(viewHolder.itemView.run { x.div(x - width) }
                    .let { if (it != 1f && it != 0f) it else coefficientRight })
                paint.color = rightContainerColor.let {
                    Color.argb((coefficientRight * it.alpha).toInt(), it.red, it.green, it.blue)
                }

                deleteIcon = AppCompatResources.getDrawable(context, R.drawable.ic_delete)!!
                deleteIcon?.setTint(context.getColor(R.color.red))
                deleteIcon?.bounds = Rect(
                    vhWidth - deleteIcon?.intrinsicWidth!! - marginHorizontal,
                    (viewHolder.itemView.top + vhHeight.div(2) - deleteIcon?.intrinsicHeight!!.div(
                        2
                    )),
                    vhWidth - marginHorizontal,
                    viewHolder.itemView.top + vhHeight.div(2) + deleteIcon?.intrinsicHeight!!.div(2)
                )
                deleteIcon?.draw(canvas)
            }

            val rect = with(viewHolder.itemView) { Rect(0, top, width, bottom) }
            canvas.drawRect(rect, paint)

            val newDx = if (dX > 0) dX / 4 else dX
            super.onChildDraw(
                canvas,
                recyclerView,
                viewHolder,
                newDx,
                dY,
                actionState,
                isCurrentlyActive
            )
        }
    }
}

/** Перевод в пиксели */
val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
