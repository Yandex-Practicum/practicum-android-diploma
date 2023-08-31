package ru.practicum.android.diploma.root.presentation.ui.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.root.presentation.model.VacancyScreenModel

class VacancyViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val employerArtworkView: ImageView = itemView.findViewById(R.id.employer_artwork)
    private val employerTitleView: TextView = itemView.findViewById(R.id.employer_title)
    private val vacancyDetailsView: TextView = itemView.findViewById(R.id.vacancy_details)

    fun bind(model: VacancyScreenModel) {
        Glide.with(itemView)
            .load(model.artworkUrl)
            .centerCrop()
            .placeholder(R.drawable.employer_placeholder)
            .into(employerArtworkView)

        employerTitleView.text = model.employer
        vacancyDetailsView.text = model.details
    }
}