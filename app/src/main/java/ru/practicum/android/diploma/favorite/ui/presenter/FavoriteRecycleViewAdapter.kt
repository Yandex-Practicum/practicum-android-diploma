package ru.practicum.android.diploma.favorite.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyCardBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.ClickListener

class FavoriteRecycleViewAdapter(
    private val list: List<VacancySearch>,
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<FavoriteVacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
        return FavoriteVacancyViewHolder(VacancyCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavoriteVacancyViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onVacancyClick(itemView)
        }

    }
}
