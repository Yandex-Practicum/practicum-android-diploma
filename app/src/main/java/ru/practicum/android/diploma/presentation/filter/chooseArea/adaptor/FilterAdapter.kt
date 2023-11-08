package ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.IndustryAreaModel

class FilterAdapter<T : IndustryAreaModel>(
    val items: ArrayList<T>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<FilterViewHolder>() {

    private var positionChecked = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder =
        FilterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.industry_area_item, parent, false)
        )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(
            items[position],
            position,
            clickListener,
            { notifyItemChanged(position) },
            { isChecked: Boolean -> setPositionChecked(position, isChecked) })

        holder.itemView.setOnClickListener {
            clickListener.onItemClicked(
                items[position],
                position,
                { notifyItemChanged(position) },
                { isChecked: Boolean -> setPositionChecked(position, isChecked) }
            )
        }
    }

    private fun setPositionChecked(position: Int, isChecked: Boolean) {
        if (positionChecked > -1) {
            items[positionChecked].isChecked = false
            notifyItemChanged(positionChecked)
        }
        positionChecked = if (isChecked) position else -1
    }

    override fun getItemCount(): Int = items.size

    fun interface ClickListener {
        fun onItemClicked(
            model: IndustryAreaModel,
            position: Int,
            notifyItemChanged: () -> Unit,
            setPositionChecked: (Boolean) -> Unit
        )
    }
}