package ru.practicum.android.diploma.presentation.favorite

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.detail.VacancyDetail

class FavoriteAdapter(
    val context: Context
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    var vacancyList = ArrayList<VacancyDetail>()
    var itemClickListener: ((Int, VacancyDetail) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder = FavoriteViewHolder(parent)

    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val vacancy = vacancyList[position]
        holder.bind(vacancy)

        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(position, vacancy)
        }
    }
}
