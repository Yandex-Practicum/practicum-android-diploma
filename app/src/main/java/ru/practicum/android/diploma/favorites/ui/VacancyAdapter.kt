package ru.practicum.android.diploma.favorites.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class VacancyAdapter(private val items: List<Vacancy>) :
    RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    inner class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.vacancy_name)
        private val company = itemView.findViewById<TextView>(R.id.company_name)
        private val salary = itemView.findViewById<TextView>(R.id.company_salary)
        private val logo = itemView.findViewById<ImageView>(R.id.company_logo)

        fun bind(vacancy: Vacancy) {
            name.text = vacancy.vacancyName
            company.text = vacancy.companyName
            salary.text = vacancy.salary
            logo.setImageResource(vacancy.logoResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_vacancy_info, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
