package ru.practicum.android.diploma.search.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyCardBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.ClickListener

class RecycleViewAdapter(
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<VacancyViewHolder>() {
    private val list = mutableListOf<VacancySearch>()

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
            clickListener.onClick(itemView)
        }

    }

    fun setList(newVacancyList: List<VacancySearch>) {
        list.clear()
        list.addAll(newVacancyList)
    }

    fun listSize(): Int {
        return list.size
    }
}
