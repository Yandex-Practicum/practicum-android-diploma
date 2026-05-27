package ru.practicum.android.diploma.vacancy.data

import android.content.Context
import ru.practicum.android.diploma.core.data.formatters.CurrencyFormatter
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.Request
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.models.Contacts
import ru.practicum.android.diploma.vacancy.domain.models.Phone
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val currencyFormatter: CurrencyFormatter,
    private val context: Context
) : VacancyDetailsRepository {
    override suspend fun getById(id: String): Resource<VacancyDetails> {
        val response = networkClient.doRequest(Request.VacancyDetailsRequest(id))

        return when (response.resultCode) {
            ResultCode.SUCCESS -> {
                val dto = response.data as VacancyDetailsDto
                Resource.Success(mapping(dto))
            }
            ResultCode.NO_INTERNET -> {
                Resource.Error(
                    code = response.resultCode
                )
            }
            ResultCode.SERVER_ERROR -> {
                Resource.Error(
                    code = response.resultCode
                )
            }
            else -> {
                Resource.Error(
                    code = response.resultCode
                )
            }
        }
    }

    private fun mapping(dto: VacancyDetailsDto): VacancyDetails {
        return VacancyDetails(
            id = dto.id,
            name = dto.name,
            salary = currencyFormatter.format(dto.salary?.from, dto.salary?.to, dto.salary?.currency),
            employerName = dto.employer.name,
            employerLogoUrl = dto.employer.logo,
            city = dto.address?.city,
            address = dto.address?.raw,
            experience = dto.experience?.name,
            schedule = dto.schedule?.name,
            employment = dto.employment?.name,
            description = dto.description,
            keySkills = dto.skills,
            contacts = Contacts(
                dto.contacts?.name,
                dto.contacts?.email,
                dto.contacts?.phones?.map {
                    Phone(
                        it.country,
                        it.city,
                        it.number,
                        it.comment
                    )
                } as List<Phone>
            ),
            alternateUrl = dto.url,
        )
    }
}
