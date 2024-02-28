package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entyti.VacancyDetailDtoEntity
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.KeySkillVacancyDetail
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Phones
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

object VacancyDetailConverter {

    fun VacancyDetailDtoEntity.mapToVacancyDetailDto(): VacancyDetailDtoResponse {
        return VacancyDetailDtoResponse(
            id = id,
            name = name,
            area = area,
            vacancyLink = vacancyLink,
            contacts = contacts,
            employer = employer,
            salary = salary,
            schedule = schedule,
            type = type,
            employment = employment,
            experience = experience,
            keySkills = keySkills,
            description = description
        )
    }

    fun VacancyDetailDtoResponse.mapToVacancyDetailDtoEntity(): VacancyDetailDtoEntity {
        return VacancyDetailDtoEntity(
            vacancyId = id,
            name = name,
            area = area,
            vacancyLink = vacancyLink,
            contacts = contacts,
            employer = employer,
            salary = salary,
            schedule = schedule,
            type = type,
            employment = employment,
            experience = experience,
            keySkills = keySkills,
            description = description
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
