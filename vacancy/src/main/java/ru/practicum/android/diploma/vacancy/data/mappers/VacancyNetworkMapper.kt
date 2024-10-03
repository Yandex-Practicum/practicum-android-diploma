package ru.practicum.android.diploma.vacancy.data.mappers

import android.content.Context
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.Utils.formatSalary
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy

object VacancyNetworkMapper {
    fun map(
        context: Context,
        vacancyNetwork: HHVacancyDetailResponse
    ): Vacancy {
        return with(vacancyNetwork) {
            Vacancy(
                idVacancy = id.toInt(),
                nameVacancy = name,
                salary = context.formatSalary(salary.from, salary.to, salary.currency),
                nameCompany = billingType.name,
                location = area.name,
                experience = experience.name,
                employment = employment.name,
                description = description,
                urlLogo = employer.logoUrls?.deg90,
                dateAddVacancy = Utils.convertTimeToMilliseconds(publishedAt)
            )
        }
    }
}
