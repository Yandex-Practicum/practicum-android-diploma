package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.IndustryResult

class IndustryInteractorImpl(
    private val industryRepository: IndustryRepository,
) : IndustryInteractor {

    override suspend fun getIndustries(): IndustryResult {
        return industryRepository.getIndustries()
    }
}
