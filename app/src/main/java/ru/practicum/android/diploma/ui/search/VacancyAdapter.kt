package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class VacancyAdapter(
    private val onClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyViewHolder>() {

    private val vacancies = mutableListOf<Vacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VacancyViewBinding.inflate(inflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position], onClick)
    }

    fun clearVacancies() = vacancies.clear()

    fun addVacancies(vacancies: List<Vacancy>) =
        this.vacancies.addAll(vacancies)

}
