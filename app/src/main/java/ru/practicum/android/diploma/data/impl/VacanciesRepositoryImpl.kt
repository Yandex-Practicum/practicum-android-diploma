package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.VacanciesSearchRequest
import ru.practicum.android.diploma.data.VacanciesSearchResponse
import ru.practicum.android.diploma.data.VacancyDetailRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.models.InfoClass
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.VacanciesRepository
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import ru.practicum.android.diploma.util.ERR_SERVER
import ru.practicum.android.diploma.util.ERR_SERVER_CONNECT
import ru.practicum.android.diploma.util.HTTP_200_OK
import ru.practicum.android.diploma.util.HTTP_NO_CONNECTION
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.VACANCY_PER_PAGE

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {
    override fun searchVacancies(options: FilterOptions): Flow<Resource<List<Vacancy>>> = flow {
        val searchRequest = VacanciesSearchRequest(getOptions(options))
        val response = networkClient.doRequest(searchRequest)
        when (response.resultCode) {
            HTTP_NO_CONNECTION -> emit(Resource.Error(ERR_SERVER_CONNECT))
            HTTP_200_OK -> {
                with(response as VacanciesSearchResponse) {
                    val data = items.map {
                        Vacancy(
                            id = it.id,
                            name = it.name,
                            areaName = it.area.name,
                            employerName = it.employer.name,
                            employerUrls = it.employer.logoUrls?.original,
                            salaryFrom = it.salaryRange?.from,
                            salaryTo = it.salaryRange?.to,
                            salaryCurr = it.salaryRange?.currency ?: "RUR"
                        )
                    }
                    emit(Resource.Success(data, response.page, response.pages))
                }
            }

            else -> emit(Resource.Error(ERR_SERVER))
        }
    }

    override fun getVacancy(vacancyId: String): Flow<Resource<VacancyDetail>> = flow {
        val searchRequest = VacancyDetailRequest(vacancyId)
        val response = networkClient.doRequest(searchRequest)
        when (response.resultCode) {
            HTTP_NO_CONNECTION -> emit(Resource.Error(ERR_SERVER_CONNECT))
            HTTP_200_OK -> {
                with(response as VacancyDetailDto) {
                    val data = VacancyDetail(
                        id = id,
                        name = name,
                        areaName = area.name,
                        employerName = employer.name,
                        employerUrls = employer.logoUrls?.original,
                        salaryFrom = salary_range.from,
                        salaryTo = salary_range.to,
                        salaryCurr = salary_range.currency,
                        keySkills = key_skills.map {
                            infoNameToString(it)
                        },
                        employmentForm = employment_form?.name.toString(),
                        professionalRoles = professional_roles,
                        experience = experience.toString(),
                        description = description
                    )
                    emit(Resource.Success(data, 0, 0))
                }
            }

            else -> emit(Resource.Error(ERR_SERVER))
        }
    }

    private fun infoNameToString(info: InfoClass): String {
        return info.name
    }

    private fun getOptions(options: FilterOptions): HashMap<String, String> {
        val hhOptions: HashMap<String, String> = HashMap()
        hhOptions["per_page"] = VACANCY_PER_PAGE
        hhOptions["text"] = options.searchText
        hhOptions["page"] = options.page.toString()
        if (options.area.isNotEmpty()) {
            hhOptions["area"] = options.area
        }
        if (options.currency.isEmpty()) {
            hhOptions["currency"] = "RUR"
        }
        if (options.salary != null) {
            hhOptions["currency"] = options.currency
        }
        if (options.industry.isNotEmpty()) {
            hhOptions["industry"] = options.industry
        }
        hhOptions["only_with_salary"] = options.onlyWithSalary.toString()
        return hhOptions
    }
}
