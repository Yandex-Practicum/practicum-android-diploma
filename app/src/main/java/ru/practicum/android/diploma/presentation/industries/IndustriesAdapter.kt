package ru.practicum.android.diploma.presentation.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal

class IndustriesAdapter() : RecyclerView.Adapter<IndustriesViewHolder>() {

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged() // Обновляем адаптер для обновления состояний элементов списка
    }

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    val industriesList = ArrayList<ParentIndustriesAllDeal>()
    var itemClickListener: ((Int, ParentIndustriesAllDeal) -> Unit)? = null
    val filteredList = ArrayList<ParentIndustriesAllDeal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustriesViewHolder(
            IndustryItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        val industries = filteredList[position]
        holder.bind(industries)
        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged() // Обновляем адаптер для обновления состояний CheckBox
            itemClickListener?.invoke(position, industries)
        }
        holder.bindSelection(selectedPosition == position)
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(text: String) {
        filteredList.clear()
        if (text.isEmpty()) {
            filteredList.addAll(industriesList)
        } else {
            for (item in industriesList) {
                if (item.name.contains(text, ignoreCase = true)) {
                    filteredList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}
