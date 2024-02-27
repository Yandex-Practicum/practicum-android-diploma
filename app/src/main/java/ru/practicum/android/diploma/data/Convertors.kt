package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.fields.AreaDto
import ru.practicum.android.diploma.data.dto.fields.DetailVacancyDto
import ru.practicum.android.diploma.data.dto.fields.EmployerDto
import ru.practicum.android.diploma.data.dto.fields.KeySkillsDto
import ru.practicum.android.diploma.data.dto.fields.PhoneNumsDto
import ru.practicum.android.diploma.data.dto.fields.VacancyDto
import ru.practicum.android.diploma.data.search.network.SearchListDto
import ru.practicum.android.diploma.domain.models.DetailVacancy
import ru.practicum.android.diploma.domain.models.SearchList
import ru.practicum.android.diploma.domain.models.Vacancy

class Convertors {
    fun convertorToVacancy(vacancy: VacancyDto): Vacancy {
        return Vacancy(
            id = vacancy.id,
            city = createAreaName(vacancy.area),
            employerLogoUrls = vacancy.employer.logoUrlsDto?.art240,
            employer = createEmployerName(vacancy.employer),
            name = vacancy.name,
            currency = vacancy.salary?.currency,
            salaryFrom = vacancy.salary?.from,
            salaryTo = vacancy.salary?.to,
        )
    }

    fun convertorToSearchList(searchList: SearchListDto): SearchList {
        return SearchList(
            found = searchList.found,
            maxPages = searchList.pages,
            currentPages = searchList.page,
            listVacancy = searchList.results.map { vacancyDto -> convertorToVacancy(vacancyDto) },
        )
    }

    fun convertorToDetailVacancy(vacancy: DetailVacancyDto): DetailVacancy {
        return DetailVacancy(
            id = vacancy.id,
            areaId = "",
            areaName = createAreaName(vacancy.area),
            areaUrl = vacancy.employer?.logoUrlsDto?.art240,
            contactsCallTrackingEnabled = false,
            contactsEmail = vacancy.contacts?.email,
            contactsName = vacancy.contacts?.name,
            contactsPhones = vacancy.contacts?.phones.let { list -> list?.map { createPhone(it) } },
            comment = null,
            description = vacancy.description,
            employerName = createEmployerName(vacancy.employer),
            employmentId = "",
            employmentName = vacancy.employment.name,
            experienceId = "",
            experienceName = vacancy.experience.name,
            keySkillsNames = createKeySkills(vacancy.keySkills),
            name = vacancy.name,
            salaryCurrency = vacancy.salary?.currency,
            salaryFrom = vacancy.salary?.from,
            salaryGross = false,
            salaryTo = vacancy.salary?.to,
            scheduleId = "",
            scheduleName = vacancy.schedule?.name,
            logoUrl = vacancy.employer?.logoUrlsDto?.original,
            logoUrl90 = vacancy.employer?.logoUrlsDto?.art90,
            logoUrl240 = vacancy.employer?.logoUrlsDto?.art240,
            employerUrl = vacancy.employer?.logoUrlsDto?.art240
        )
    }

    private fun createAreaName(area: AreaDto?): String? {
        return if (area?.name == null) {
            null
        } else {
            area.name
        }
    }

    private fun createEmployerName(employerName: EmployerDto?): String? {
        return if (employerName?.name == null) {
            null
        } else {
            employerName.name
        }
    }

    private fun createPhone(phone: PhoneNumsDto): String {
        return "+${phone.country}" + " (${phone.city})" + " ${phone.number}"
    }

    private fun createKeySkills(keySkills: List<KeySkillsDto>?): List<String>? {
        return (listOf((keySkills?.map { it.name } ?: emptyList()).toString()))!!
    }

}
