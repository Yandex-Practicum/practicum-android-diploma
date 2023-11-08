package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.models.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.filter.FilterRepository

class FilterInteractorImpl(val repository: FilterRepository) : FilterInteractor{
    override fun setSalary(input: String) {
        repository.setSalary(input)
    }

    override fun getSalary(): String{
        return repository.getSalary()
    }

}