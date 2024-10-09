package ru.practicum.android.diploma.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.filter.domain.usecase.FilterInteractor

class FilterInteractorImpl(
    private val repository: FilterRepository
) : FilterInteractor {
}
