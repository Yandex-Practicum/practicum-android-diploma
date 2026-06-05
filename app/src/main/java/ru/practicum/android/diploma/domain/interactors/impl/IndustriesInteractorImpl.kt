package ru.practicum.android.diploma.domain.interactors.impl

import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.interactors.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(
    private val repository: IndustriesRepository,
) : IndustriesInteractor {

    override suspend fun getIndustries(): Resource<List<Industry>> {
        return repository.getIndustries()
    }
}
