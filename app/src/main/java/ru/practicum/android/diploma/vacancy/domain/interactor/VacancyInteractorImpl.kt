package ru.practicum.android.diploma.vacancy.domain.interactor

import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyInteractorImpl(
    private val vacancyRepository: VacancyRepository
) : VacancyInteractor
