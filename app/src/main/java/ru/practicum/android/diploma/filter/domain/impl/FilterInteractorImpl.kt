package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import javax.inject.Inject

class FilterInteractorImpl @Inject constructor
    (val filterRepository: FilterRepository) :
    FilterInteractor {
    override fun filter() {

    }
}