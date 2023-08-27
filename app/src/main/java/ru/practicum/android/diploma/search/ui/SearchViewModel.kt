package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.SearchVacanciesUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchVacanciesUseCase: SearchVacanciesUseCase, logger: Logger): BaseViewModel(logger) {
    init {
        viewModelScope.launch {
            searchVacanciesUseCase.search("nirvana")
        }
    }
}