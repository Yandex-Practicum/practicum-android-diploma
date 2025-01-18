package ru.practicum.android.diploma.search.domain.interactor

import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchInteractorImpl(
    private val searchRepository: SearchRepository
) : SearchInteractor
