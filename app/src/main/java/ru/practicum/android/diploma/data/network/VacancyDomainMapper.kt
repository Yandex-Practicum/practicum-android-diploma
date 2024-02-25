package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.vacancylist.dto.VacancyRemote
import ru.practicum.android.diploma.domain.models.main.Vacancy

fun VacancyRemote.asDomain(): Vacancy {
    val salary = StringBuilder()

    salary.append("${this.salary} ")
    this.salary.to?.let {
        salary.append("- $it ")
    }
    salary.append(this.salary.currency)

    return Vacancy(
        id = this.id,
        employerImgUrl = this.employer.logoUrl.medium,
        employer = this.employer.name,
        name = this.name,
        salary = salary.toString(),
        area = this.address.city)
}

fun List<VacancyRemote>.asDomain(): List<Vacancy> = this.map { it.asDomain() }
