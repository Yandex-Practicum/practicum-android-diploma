package ru.practicum.android.diploma.search.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.databinding.VacancyViewBinding

class VacancyAdapter(
    private val context: Context,
    var onItemClick: ((ShortVacancy) -> Unit)? = null
) : RecyclerView.Adapter<VacancyViewHolder>() {

    var vacancyList = ArrayList<ShortVacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = VacancyViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return VacancyViewHolder(binding, context)
    }

    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(vacancyList[position])
        }
    }
}
