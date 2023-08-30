package ru.practicum.android.diploma.root.presentation.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.root.presentation.model.VacancyScreenModel

class VacancyAdapter(
    private val vacancyList: ArrayList<VacancyScreenModel>,
    private val onItemClickedAction: (String) -> Unit
) : RecyclerView.Adapter<VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vacancy_rw_card, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyList[position])
        holder.itemView.setOnClickListener {
            onItemClickedAction.invoke(vacancyList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    fun updateItems(items: ArrayList<VacancyScreenModel>) {
        vacancyList.clear()
        vacancyList.addAll(items)
    }

    fun addItems(items: ArrayList<VacancyScreenModel>) {
        vacancyList.addAll(items)
    }
}