package ru.practicum.android.diploma.presentation.favorite

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.main.Vacancy

class FavoriteAdapter(
    val context: Context
): RecyclerView.Adapter<FavoriteViewHolder>() {

    var vacancy = ArrayList<Vacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder = FavoriteViewHolder(parent)

    override fun getItemCount(): Int = vacancy.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(vacancy[position])
    }
}
