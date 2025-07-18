package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.vacancy.vacancydetails.VacancyDetailsDto
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.detailsvacancy.DetailsVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.toCurrencySymbol

class DetailsVacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : DetailsVacancyRepository {
    override suspend fun doRequest(vacancyId: String): Result<VacancyDetails> {
        return withContext(Dispatchers.IO) {
            networkClient.detailsVacancyRequest(vacancyId)
                .map { dto -> mapResponse(dto) }
        }
    }

    private fun mapResponse(dto: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            vacancyId = dto.id,
            vacancyName = dto.name,
            employerName = dto.employer?.name,
            employerLogo = dto.employer?.employerLogo?.path,
            address = when (dto.address?.raw) {
                null -> dto.area.name
                else -> dto.address.raw
            },
            salaryFrom = dto.salary?.from,
            salaryTo = dto.salary?.to,
            currency = dto.salary?.currency.toCurrencySymbol(),
            employmentForm = dto.employmentForm?.name,
            workFormat = dto.workFormat?.map { it.name },
            experience = dto.experience?.name,
            linkUrl = dto.linkUrl,
            description = dto.description,
            keySkills = dto.keySkills.map { it.name }
        )
    }
}
