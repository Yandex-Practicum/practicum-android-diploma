package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entyti.VacancyDetailDtoEntity
import ru.practicum.android.diploma.data.dto.responseUnits.Employer
import ru.practicum.android.diploma.data.dto.responseUnits.LogoUrls
import ru.practicum.android.diploma.data.dto.responseUnits.Salary
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyArea
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyType
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Contacts
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Employment
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Experience
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Phones
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Schedule
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Snippet
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto

object VacancyDetailsDbConverter {

    fun VacancyDetailDtoEntity.mapToVacancyDetailDto(): VacancyDetailDto {
        return VacancyDetailDto(
            id = vacancyId,
            name = name,
            area = VacancyArea(vacancyId, name, url),
            contacts = Contacts("", "", arrayOf(Phones("", "", "", "", ""))),
            employer = Employer("", LogoUrls("", "", ""), "", false, ""),
            salary = Salary("", 0, false, 0),
            schedule = Schedule("", ""),
            type = VacancyType("", ""),
            employment = Employment("", ""),
            experience = Experience("", ""),
            snippet = Snippet("", ""),
            keySkills = listOf(),
            url = url,
            description = description
        )
    }

    fun VacancyDetailDto.mapToVacancyDetailDtoEntity(): VacancyDetailDtoEntity {
        return VacancyDetailDtoEntity(
            vacancyId = id,
            url = employer.logoUrls?.original ?: "",
            name = name,
            area = area.name,
            salaryCurrency = salary?.currency ?: "",
            salaryFrom = salary?.from,
            salaryTo = salary?.to,
            salaryGross = salary?.gross,
            experience = experience?.name,
            schedule = schedule?.name,
            contactName = contacts?.name,
            contactEmail = contacts?.email,
            phones = employer.logoUrls?.original,
            contactComment = contacts?.name,
            logoUrl = employer.logoUrls?.original,
            logoUrl90 = employer.logoUrls?.art90,
            logoUrl240 = employer.logoUrls?.art240,
            address = name,
            employerUrl = employer.logoUrls?.original,
            employerName = employer.name,
            employment = employment?.name,
            keySkills = keySkills.toString(),
            description = description,
        )
    }

    fun List<VacancyDetailDtoEntity>.mapToVacancyDetailDto(): List<VacancyDetailDto> =
        map { it.mapToVacancyDetailDto() }

}
