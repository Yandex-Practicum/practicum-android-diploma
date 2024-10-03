package ru.practicum.android.diploma.search.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyCardBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch

class RecycleViewAdapter(
    private val list: List<VacancySearch>,
    private val clickListener: VacancyClickListener
) :
    RecyclerView.Adapter<VacancyViewHolder>() {
    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: VacancySearch)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
        return VacancyViewHolder(VacancyCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onVacancyClick(itemView)
        }

    }
}
