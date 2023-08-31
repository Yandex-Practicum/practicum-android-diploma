package ru.practicum.android.diploma.similars

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SimilarVacanciesViewModel @Inject constructor(
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState = MutableStateFlow<SimilarVacanciesState>(SimilarVacanciesState.Empty)
    val uiState: StateFlow<SimilarVacanciesState> = _uiState

    fun getSimilarVacancies(vacancyId: Long) {
        log(thisName, "getSimilarVacancies(vacancyId: $vacancyId)")
        mok()
    }

    /** Моковые данные */
    private fun mok() {
        val similarVacancies = Vacancy(
            id = 1,
            area = "Москва",
            title = "Менеджер по продажам",
            salary = "от 50 000 руб.",
            company = "ООО «Рога и копыта»",
            iconUri = "",
            date = 3L)

        _uiState.value = SimilarVacanciesState.Content(listOf(similarVacancies))
    }


}