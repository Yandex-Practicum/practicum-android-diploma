package ru.practicum.android.diploma.ui.search.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(
    private val onProductClick: (vacancy: Vacancy) -> Unit,
) : RecyclerView.Adapter<VacancyViewHolder>() {
    private var vacancies: List<Vacancy> = emptyList()

    fun setItems(items: List<Vacancy>) {
        vacancies = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): VacancyViewHolder {
        val binding = ItemVacancyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VacancyViewHolder(binding, onProductClick)
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        vacancies.getOrNull(position)?.let { track ->
            holder.bind(track)
        }
    }
}
