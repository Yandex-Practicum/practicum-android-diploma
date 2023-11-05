package ru.practicum.android.diploma.presentation.similar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.mok.Vacancy

class VacanciesAdapter (private val listener: ClickListener
): RecyclerView.Adapter<VacancyViewHolder> () {

    private var vacancies = listOf<Vacancy>()
        set(newValue) {
            val diffCallBack = VacancyDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }


    fun interface ClickListener {
        fun onClick(vacancy: Vacancy)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = ItemVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position], listener)
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

}