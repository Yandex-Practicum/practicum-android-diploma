package ru.practicum.android.diploma.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    private var vacancyList = mutableListOf<Vacancy>()

    inner class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val vacancyNameAndArea: TextView = itemView.findViewById(R.id.vacancyNameAndArea)

        fun bind(vacancy: Vacancy) {
            vacancyNameAndArea.text =
                itemView.resources.getString(R.string.item_vacancy_name, vacancy.name, vacancy.area)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyList[position])
    }
}
