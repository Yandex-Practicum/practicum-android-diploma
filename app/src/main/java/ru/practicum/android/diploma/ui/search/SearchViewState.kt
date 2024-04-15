package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

sealed interface SearchViewState {
    // Cписок ваканисй
    data class Content(val vacancies: List<Vacancy>, val found: Int) : SearchViewState

    // Прогресс бар
    data object Loading : SearchViewState

    data object RecyclerLoading : SearchViewState

    // Картинка нет интернета
    data object NoInternet : SearchViewState

    // Катинка ничего не найдено
    data object EmptyVacancies : SearchViewState

    // Картинка по умолчанию, котрорая с биноклем
    data object Default : SearchViewState
}
