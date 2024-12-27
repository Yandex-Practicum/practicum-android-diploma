package ru.practicum.android.diploma.data.vacancy.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.model.KeySkillsDto
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.dto.request.VacancyRequest
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.Salary

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {

    override fun getVacancyId(id: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequest(VacancyRequest(id))
        when (response.code) {
            RetrofitNetworkClient.INTERNET_NOT_CONNECT -> {
                emit(Resource.Error(VacancyError.NETWORK_ERROR))
            }

            RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE -> {
                emit(Resource.Error(VacancyError.BAD_REQUEST))
            }

            RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE -> {
                emit(Resource.Error(VacancyError.NOT_FOUND))
            }

            RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE -> {
                emit(Resource.Error(VacancyError.SERVER_ERROR))
            }

            RetrofitNetworkClient.HTTP_OK_CODE -> {
                emit(Resource.Success(convertFromServerToAppEntity((response as VacancyResponse).items)))
            }

            else -> {
                emit(Resource.Error(VacancyError.UNKNOWN_ERROR))
            }
        }
    }

    private fun convertFromServerToAppEntity(vacancyForConvert: VacancyFullItemDto): Vacancy {
        return Vacancy(
            id = vacancyForConvert.id,
            titleOfVacancy = vacancyForConvert.name,
            regionName = getCorrectFormOfAddress(vacancyForConvert),
            salary = Salary().salaryFun(vacancyForConvert.salary),
            employerName = vacancyForConvert.employer.name,
            employerLogoUrl = vacancyForConvert.employer.logoUrls?.original,
            experience = vacancyForConvert.experience.name,
            employmentType = vacancyForConvert.employment.name,
            scheduleType = vacancyForConvert.schedule.name,
            keySkills = getCorrectFormOfKeySkills(vacancyForConvert.keySkills),
            description = vacancyForConvert.description,
            alternateUrl = vacancyForConvert.vacancyUrl
        )
    }

    private fun getCorrectFormOfKeySkills(keySkills: List<KeySkillsDto>): String? {
        return if (keySkills.isEmpty()) {
            null
        } else {
            keySkills.joinToString(separator = "\n") { itemKey ->
                "• ${itemKey.name.replace(",", ",\n")}"
            }
        }
    }

    private fun getCorrectFormOfAddress(convertingVacancy: VacancyFullItemDto): String {
        return if (convertingVacancy.address == null) {
            convertingVacancy.area.name
        } else {
            convertingVacancy.address.raw
        }
    }
}
