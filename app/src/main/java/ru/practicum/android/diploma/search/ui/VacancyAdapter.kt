package ru.practicum.android.diploma.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class VacancyAdapter: RecyclerView.Adapter<VacancyViewHolder>() {
    var items: MutableList<VacancyItems> = mutableListOf()
        set(value) {
            field = value.toMutableList()
            notifyDataSetChanged()
        }

    var onItemClickListener: VacancyViewHolder.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
