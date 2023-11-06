package ru.practicum.android.diploma.domain.detail.impl

import ru.practicum.android.diploma.domain.detail.DetailRepository
import ru.practicum.android.diploma.domain.detail.DetailInteractor
import ru.practicum.android.diploma.domain.models.detail.Vacancy

class DetailInteractorImpl(val repository: DetailRepository) : DetailInteractor {
    override fun getVacancy(): Vacancy {
        return repository.getVavancy()
    }

}