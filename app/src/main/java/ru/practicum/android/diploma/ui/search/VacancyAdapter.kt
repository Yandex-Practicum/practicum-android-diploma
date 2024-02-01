package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(private val clickListener: VacancyClickListener) : RecyclerView.Adapter<VacancyViewHolder>() {
    private var vacancyList = ArrayList<Vacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyList[position])
        holder.itemView.setOnClickListener { clickListener.onTrackClick(vacancyList[position]) }
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    fun interface VacancyClickListener {
        fun onTrackClick(vacancy: Vacancy)
    }
}
