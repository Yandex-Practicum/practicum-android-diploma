package ru.practicum.android.diploma.presentation.filter.chooseArea.adaptor

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.IndustryAreaModel

class FilterAdapter<T : IndustryAreaModel>(
    val items: ArrayList<T>,
    private val clickListener: (T) -> Unit
) : RecyclerView.Adapter<FilterViewHolder>() {

    private var positionChecked = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder =
        FilterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.industry_area_item, parent, false)
        )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(items[position])
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

}