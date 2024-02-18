package ru.practicum.android.diploma.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacancyModel
import ru.practicum.android.diploma.ui.search.view_holder.VacanciesViewHolder

class VacanciesAdapter(
    private val vacancies: ArrayList<VacancyModel>,
    private val itemClickListener: ((VacancyModel) -> Unit)
) : RecyclerView.Adapter<VacanciesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VacanciesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VacanciesViewHolder(VacancyItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(
        holder: VacanciesViewHolder,
        position: Int
    ) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)
        holder.itemView.setOnClickListener {
            itemClickListener.invoke(vacancy)
        }
    }

}
