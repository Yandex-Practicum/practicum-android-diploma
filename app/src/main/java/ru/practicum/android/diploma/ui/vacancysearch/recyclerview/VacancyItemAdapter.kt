package ru.practicum.android.diploma.ui.vacancysearch.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel

class VacancyItemAdapter(
    private val vacancies: MutableList<VacancyUiModel>,
    private val listener: Listener
) : RecyclerView.Adapter<VacancyItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyItemViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)
        holder.itemView.setOnClickListener {
            listener.onClick()
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    interface Listener{
        fun onClick()
    }
}
