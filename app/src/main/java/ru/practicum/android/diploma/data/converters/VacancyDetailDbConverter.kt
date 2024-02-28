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
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.KeySkillVacancyDetail
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Phones
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Schedule
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

object VacancyDetailDbConverter {

    fun VacancyDetailDtoEntity.mapToVacancyDetailDto(): VacancyDetailDtoResponse {
        return VacancyDetailDtoResponse(
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
            keySkills = listOf(),
            vacancyLink = url,
            description = description
        )
    }

    fun VacancyDetailDtoResponse.mapToVacancyDetailDtoEntity(): VacancyDetailDtoEntity {
        return VacancyDetailDtoEntity(
            vacancyId = id,
            url = employer?.logoUrls?.original ?: "",
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
            phones = employer?.logoUrls?.original,
            contactComment = contacts?.name,
            logoUrl = employer?.logoUrls?.original,
            logoUrl90 = employer?.logoUrls?.art90,
            logoUrl240 = employer?.logoUrls?.art240,
            address = name,
            employerUrl = employer?.logoUrls?.original,
            employerName = employer?.name,
            employment = employment?.name,
            keySkills = keySkills.toString(),
            description = description,
        )
    }

    fun List<VacancyDetailDtoEntity>.mapToVacancyDetailDto(): List<VacancyDetailDtoResponse> =
        map { it.mapToVacancyDetailDto() }

    fun VacancyDetailDtoResponse.toVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = id,
            name = name,
            area = area.name,
            vacancyLink = vacancyLink,
            contactName = contacts?.name,
            contactEmail = contacts?.email,
            contactPhone = "buildPhoneNumbers(contacts?.phones)" ,
            contactComment = "buildPhoneComments(contacts?.phones)",
            employerName = employer?.name,
            employerUrl = employer?.logoUrls?.original,
            salary = "от ${salary?.from} до ${salary?.to}",
            schedule = schedule?.name,
            employment = employment?.name,
            experience = experience?.name,
            keySkills = "buildKeySkills(keySkills)",
            description = description
        )
    }

    fun buildPhoneNumbers(phones: Array<Phones>?): List<String?>{
        var phoneString: String
        val phoneList = mutableListOf<String>()
        phones?.forEach {
            phoneString = "+${it.country} (${it.city}) ${it.number}"
            phoneList.add(phoneString)
        }
        return phoneList
    }

    fun buildPhoneComments(phones: Array<Phones>?): List<String?>{
        val commentList = mutableListOf<String?>()
        phones?.forEach {
            commentList.add(it.comment)
        }
        return commentList
    }

    fun buildKeySkills(keySkills: List<KeySkillVacancyDetail>): List<String>{
        val skillsList = mutableListOf<String>()
        keySkills.forEach {
            skillsList.add(it.name)
        }
        return skillsList
    }
}
