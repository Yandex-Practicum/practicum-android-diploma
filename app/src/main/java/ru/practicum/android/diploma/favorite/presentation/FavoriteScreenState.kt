package ru.practicum.android.diploma.favorite.presentation

import ru.practicum.android.diploma.search.domain.models.VacancySearch

sealed class FavoriteScreenState {
    data class ContentState(val vacancies: List<VacancySearch>) : FavoriteScreenState()
    object EmptyState : FavoriteScreenState()
    object ErrorState : FavoriteScreenState()
}
