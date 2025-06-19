package ru.practicum.android.diploma.ui.favorite.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteAdapter(private val clickListener: FavoriteClickListener) : RecyclerView.Adapter<FavoriteViewHolder>() {
    val vacancies: MutableList<VacancyDetails> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        return FavoriteViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener { clickListener.onFavoriteClick(vacancies[position]) }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    fun interface FavoriteClickListener {
        fun onFavoriteClick(vacancy: VacancyDetails)
    }
}
