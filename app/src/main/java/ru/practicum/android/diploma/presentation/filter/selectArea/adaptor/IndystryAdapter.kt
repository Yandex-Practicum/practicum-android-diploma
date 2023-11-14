package ru.practicum.android.diploma.presentation.filter.selectArea.adaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Industry
import java.util.Locale

class IndystryAdapter(
    private var items: ArrayList<Industry>,
    private val clickListener: (Industry) -> Unit
) : RecyclerView.Adapter<IndystryViewHolder>() {

    private var positionChecked = -1

    private var filteredItems: ArrayList<Industry> = ArrayList(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndystryViewHolder =
        IndystryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.industry_area_item, parent, false),
            clickListener
        )

    override fun onBindViewHolder(holder: IndystryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Industry>) {
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


    override fun getItemCount(): Int = filteredItems.size

}

