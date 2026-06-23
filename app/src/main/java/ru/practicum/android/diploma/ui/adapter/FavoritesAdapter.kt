package ru.practicum.android.diploma.ui.adapter

import ru.practicum.android.diploma.domain.models.VacancyCard

class FavoritesAdapter(val clickListener: VacancyClickListener) {
        fun interface VacancyClickListener {
            fun onVacancyClick(vacancy: VacancyCard)
        }

}
