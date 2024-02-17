package ru.practicum.android.diploma.commons.data.network

import android.icu.util.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commons.data.dto.Response
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.Salary
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.VacanciesSearchByNameRequest
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.VacanciesSearchResponse
import ru.practicum.android.diploma.commons.data.dto.vacancies_search.VacanciesSearchSimilarRequest
import ru.practicum.android.diploma.commons.data.dto.vacancy_detailed.VacancyDetailedRequest
import ru.practicum.android.diploma.commons.data.dto.vacancy_detailed.VacancyDetailedResponse
import ru.practicum.android.diploma.commons.domain.api.VacanciesRepository
import ru.practicum.android.diploma.commons.domain.model.Details
import ru.practicum.android.diploma.commons.domain.model.VacanciesModel
import ru.practicum.android.diploma.commons.domain.model.VacancyModel

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {

    private fun salaryToString(
        salary: Salary?
    ): String {
        var salaryAsString = ""
        if (salary?.from == null && salary?.to == null) {
            salaryAsString = "Зарплата не указана"
        } else {
            if (salary.from != null) {
                salaryAsString += "от" + salary.from + " "
            }
            if (salary.to != null) {
                salaryAsString += "до" + salary.to + " "
            }
            if (salary.currency != null) {
                salaryAsString += Currency.getInstance(salary.currency).symbol
            }
        }
        return salaryAsString
    }

    private fun shallowVacanciesInitialization(
        response: Response
    ): ArrayList<VacancyModel> {
        val results = (response as VacanciesSearchResponse).results

        val vacancies = results.vacancies
        val domainVacancies = arrayListOf<VacancyModel>()

        vacancies.forEach { vacancy ->
            domainVacancies.add(VacancyModel(
                id = vacancy.id,
                vacancyName = vacancy.name,
                city = vacancy.address?.city ?: "Город не указан",
                salary = salaryToString(vacancy.salary),
                companyName = vacancy.company.name,
                logoUrls = if (vacancy.company.logoUrls == null) arrayListOf() else arrayListOf(vacancy.company.logoUrls.url90, vacancy.company.logoUrls.url240, vacancy.company.logoUrls.urlOriginal),
                details = null
            ))
        }

        return domainVacancies
    }

    private fun getPages(
        response: Response
    ) = (response as VacanciesSearchResponse).results.pages

    override fun searchVacancies(
        name: String,
        page: Long,
        amount: Long
    ): Flow<Pair<VacanciesModel, Int>> = flow {
        val response = networkClient.doRequest(VacanciesSearchByNameRequest(name, page, amount))

        if (response.responseCode == 200) {
            val vacancies = shallowVacanciesInitialization(response)
            emit(Pair(VacanciesModel(getPages(response), vacancies), response.responseCode))
        } else {
            emit(Pair(VacanciesModel(-1, arrayListOf()), response.responseCode))
        }
    }

    override fun searchSimilarVacancies(
        id: String,
        page: Long,
        amount: Long
    ): Flow<Pair<VacanciesModel, Int>> = flow {
        val response = networkClient.doRequest(VacanciesSearchSimilarRequest(id, page, amount))

        if (response.responseCode == 200) {
            val vacancies = shallowVacanciesInitialization(response)
            emit(Pair(VacanciesModel(getPages(response), vacancies), response.responseCode))
        } else {
            emit(Pair(VacanciesModel(-1, arrayListOf()), response.responseCode))
        }
    }

    override fun searchConcreteVacancy(
        id: String
    ): Flow<Pair<VacancyModel?, Int>> = flow {
        val response = networkClient.doRequest(VacancyDetailedRequest(id))
        if (response.responseCode == 200) {
            val information = (response as VacancyDetailedResponse).information

            val domainKeySkill = arrayListOf<String>()
            information.keySkills.forEach { skill ->
                domainKeySkill.add(skill.name)
            }

            val domainPhonesAndComments = arrayListOf<Pair<String, String>>()
            information.contacts?.phones?.forEach { phone ->
                domainPhonesAndComments.add(Pair("+" + phone.country + " (" + phone.city + ") " + phone.number, phone.comment ?: "Не указан"))
            }

            emit(Pair(
                VacancyModel(
                    id = information.id,
                    vacancyName = information.name,
                    city = information.address?.city ?: "Город не указан",
                    salary = salaryToString(information.salary),
                    companyName = information.company.name,
                    logoUrls = if (information.company.logoUrls == null) arrayListOf() else arrayListOf(information.company.logoUrls.url90, information.company.logoUrls.url240, information.company.logoUrls.urlOriginal),
                    details = Details(
                        experience = information.experience?.name ?: "Не указан",
                        employment = information.employment?.name ?: "Не указан",
                        description = information.description,
                        keySkills = domainKeySkill,
                        managerName = information.contacts?.name ?: "Не указано",
                        email = information.contacts?.email ?: "Не указан",
                        phonesAndComments = domainPhonesAndComments
                    )
                ),
                response.responseCode
            ))
        } else {
            emit(Pair(null, response.responseCode))
        }
    }

}
