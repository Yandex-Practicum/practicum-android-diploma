package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel()
