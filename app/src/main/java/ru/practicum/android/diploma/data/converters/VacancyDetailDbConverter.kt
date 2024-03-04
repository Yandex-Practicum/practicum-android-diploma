package ru.practicum.android.diploma.data.converters

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.entyti.VacancyDetailEntity
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
    private val itemType = object : TypeToken<List<String?>>() {}.type

    fun VacancyDetailEntity.mapToVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = id,
            name = name,
            area = area,
            vacancyLink = vacancyLink,
            contactName = contactName,
            contactEmail = contactEmail,
            contactPhones = createListFromJson(contactPhone),
            contactComments = createListFromJson(contactComment),
            employerName = employerName,
            employerUrl = employerUrl,
            salary = salary,
            schedule = schedule,
            employment = employment,
            experience = experience,
            keySkills = createListFromJson(keySkills),
            description = description,
            isFavorite = true
        )
    }

    fun VacancyDetail.mapToVacancyDetailEntity(): VacancyDetailEntity {

        return VacancyDetailEntity(
            id = id,
            name = name,
            area = area,
            vacancyLink = vacancyLink,
            contactName = contactName,
            contactEmail = contactEmail,
            contactPhone = createJsonFromList(contactPhones),
            contactComment = createJsonFromList(contactComments),
            employerName = employerName,
            employerUrl = employerUrl,
            salary = salary,
            schedule = schedule,
            employment = employment,
            experience = experience,
            keySkills = createJsonFromList(keySkills),
            description = description,
            isFavorite = true
        )
    }

    fun List<VacancyDetailEntity>.mapToVacancyDetail(): List<VacancyDetail> =
        map { it.mapToVacancyDetail() }

    fun VacancyDetailDtoResponse.toVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = id,
            name = name,
            area = area.name,
            vacancyLink = vacancyLink,
            contactName = contacts?.name,
            contactEmail = contacts?.email,
            contactPhones = buildPhoneNumbers(contacts?.phones),
            contactComments = buildPhoneComments(contacts?.phones),
            employerName = employer?.name,
            employerUrl = employer?.logoUrls?.original,
            salary = VacancyConverter.formatSalary(salary),
            schedule = schedule?.name,
            employment = employment?.name,
            experience = experience?.name,
            keySkills = buildKeySkills(keySkills),
            description = description
        )
    }

    fun buildPhoneNumbers(phones: List<Phones>?): List<String?> {
        var phoneString: String
        val phoneList = mutableListOf<String>()
        phones?.forEach {
            phoneString = "+${it.country} (${it.city}) ${it.number}"
            phoneList.add(phoneString)
        }
        return phoneList
    }

    fun buildPhoneComments(phones: List<Phones>?): List<String?> {
        val commentList = mutableListOf<String?>()
        phones?.forEach {
            commentList.add(it.comment)
        }
        return commentList
    }

    fun buildKeySkills(keySkills: List<KeySkillVacancyDetail>): List<String> {
        val skillsList = mutableListOf<String>()
        keySkills.forEach {
            skillsList.add(it.name)
        }
        return skillsList
    }

    fun createJsonFromList(list: List<String?>): String {
        return Gson().toJson(list)
    }

    fun createListFromJson(json: String?): List<String?>{
        return Gson().fromJson(json, itemType)
    }
}
