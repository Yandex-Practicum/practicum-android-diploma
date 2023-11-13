package ru.practicum.android.diploma.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.SalaryPresenter

class SearchAdapter(
    private val data: List<Vacancy>,
    private val salaryPresenter: SalaryPresenter,
    private val clickListener: (Vacancy) -> Unit
) :
    RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val layoutInspector = LayoutInflater.from(parent.context)
        return SearchViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false),salaryPresenter)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
