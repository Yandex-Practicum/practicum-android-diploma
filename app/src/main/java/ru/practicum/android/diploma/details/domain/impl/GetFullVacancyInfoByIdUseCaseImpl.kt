package ru.practicum.android.diploma.details.domain.impl

import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.api.GetFullVacancyInfoByIdUseCase
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class GetFullVacancyInfoByIdUseCaseImpl @Inject constructor(
    private val repository: DetailsRepository
) : GetFullVacancyInfoByIdUseCase {
    override suspend fun invoke(id: String): Either<Failure, VacancyFullInfo> {
        return repository.getFullVacancyInfo(id)
    }
}