package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.IndustryAreaModel
import java.util.Locale

class FilterAdapter<T : IndustryAreaModel>(
    private var items: ArrayList<T>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<FilterViewHolder>() {

    private var positionChecked = -1

    private var filteredItems: ArrayList<T> = ArrayList(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder =
        FilterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.industry_area_item, parent, false)
        )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(
            filteredItems[position],
            position,
            clickListener,
            { notifyItemChanged(position) },
            { isChecked: Boolean -> setPositionChecked(position, isChecked) })

        holder.itemView.setOnClickListener {
            clickListener.onItemClicked(
                filteredItems[position],
                position,
                { notifyItemChanged(position) },
                { isChecked: Boolean -> setPositionChecked(position, isChecked) }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<T>) {
        items = ArrayList(newData)
        filter("") // Примените фильтрацию с пустым запросом, чтобы обновить filteredItems
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredItems.clear()
        if (query.isEmpty()) {
            filteredItems.addAll(items)
        } else {
            val lowerCaseQuery = query.lowercase(Locale.ROOT)
            items.forEach {
                if (it.name.lowercase(Locale.ROOT).contains(lowerCaseQuery)) {
                    filteredItems.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun setPositionChecked(position: Int, isChecked: Boolean) {
        if (positionChecked > -1) {
            filteredItems[positionChecked].isChecked = false
            notifyItemChanged(positionChecked)
        }
        positionChecked = if (isChecked) position else -1
    }

    override fun getItemCount(): Int = filteredItems.size

    fun interface ClickListener {
        fun onItemClicked(
            model: IndustryAreaModel,
            position: Int,
            notifyItemChanged: () -> Unit,
            setPositionChecked: (Boolean) -> Unit
        )
    }
}

