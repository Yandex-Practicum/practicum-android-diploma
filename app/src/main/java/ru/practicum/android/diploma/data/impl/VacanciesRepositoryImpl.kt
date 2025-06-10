package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.VacanciesSearchRequest
import ru.practicum.android.diploma.data.VacanciesSearchResponse
import ru.practicum.android.diploma.data.VacancyDetailRequest
import ru.practicum.android.diploma.data.models.InfoClass
import ru.practicum.android.diploma.data.models.VacancyDetailDto
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.VacanciesRepository
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import ru.practicum.android.diploma.util.ERR_SERVER
import ru.practicum.android.diploma.util.ERR_SERVER_CONNECT
import ru.practicum.android.diploma.util.Resource

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {
    override fun searchVacancies(options: FilterOptions): Flow<Resource<List<Vacancy>>> = flow {
        val searchRequest = VacanciesSearchRequest(
            BuildConfig.HH_ACCESS_TOKEN,
            BuildConfig.APPLICATION_ID,
            getOptions(options)
        )
        val response = networkClient.doRequest(searchRequest)
        when (response.resultCode) {
            -1 -> emit(Resource.Error(ERR_SERVER_CONNECT))
            200 -> {
                with(response as VacanciesSearchResponse) {
                    val data = items.map {
                        Vacancy(
                            id = it.id,
                            name = it.name,
                            areaName = it.area.name,
                            employerName = it.employer.name,
                            employerUrls = it.employer.logo_urls?.original,
                            salaryFrom = it.salary_range.from,
                            salaryTo = it.salary_range.to,
                            salaryCurr = it.salary_range.currency
                        )
                    }
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(ERR_SERVER))
        }
    }

    override fun getVacancy(vacancyId: String): Flow<Resource<VacancyDetail>> = flow {
        val searchRequest = VacancyDetailRequest(
            BuildConfig.HH_ACCESS_TOKEN,
            BuildConfig.APPLICATION_ID,
            vacancyId
        )
        val response = networkClient.doRequest(searchRequest)
        when (response.resultCode) {
            -1 -> emit(Resource.Error(ERR_SERVER_CONNECT))
            200 -> {
                with(response as VacancyDetailDto) {
                    val data = VacancyDetail(
                        id = id,
                        name = name,
                        areaName = area.name,
                        employerName = employer.name,
                        employerUrls = employer.logo_urls?.original,
                        salaryFrom = salary_range.from,
                        salaryTo = salary_range.to,
                        salaryCurr = salary_range.currency,
                        keySkills = key_skills.map {
                            infoToList(it)
                        },
                        employmentForm = employment_form?.name.toString(),
                        professionalRoles = professional_roles,
                        experience = experience.toString(),
                        description = description
                    )
                    emit(Resource.Success(data))
                }
            }
            else -> emit(Resource.Error(ERR_SERVER))
        }
    }

    private fun infoToList(info: InfoClass): String {
        return info.name
    }

    private fun getOptions(options: FilterOptions): HashMap<String, String> {
        val hhOptions: HashMap<String, String> = HashMap()
        hhOptions["text"] = options.text
        if (options.area.isNotEmpty()) {
            hhOptions["area"] = options.area
        }
        if (options.currency.isEmpty() && options.salary.isNotEmpty()) {
            hhOptions["currency"] = "RUR"
        } else if (options.currency.isNotEmpty()) {
            hhOptions["currency"] = options.currency
        }
        if (options.salary.isNotEmpty()) {
            hhOptions["salary"] = options.salary
        }
        if (options.industry.isNotEmpty()) {
            hhOptions["industry"] = options.industry
        }
        if (options.onlyWithSalary.isEmpty()) {
            hhOptions["only_with_salary"] = "false"
        } else {
            hhOptions["only_with_salary"] = "true"
        }
        return hhOptions
    }
}
