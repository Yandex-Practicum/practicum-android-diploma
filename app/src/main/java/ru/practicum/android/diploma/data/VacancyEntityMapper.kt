package ru.practicum.android.diploma.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Contact
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import java.lang.reflect.Type

class VacancyEntityMapper(private val gson: Gson) {
    fun toVacancyEntity(fullVacancy: FullVacancy): VacancyEntity {
        return VacancyEntity(
            fullVacancy.id,
            fullVacancy.name,
            fullVacancy.city,
            fullVacancy.employerName,
            fullVacancy.employerLogoUrl ?: "",
            gson.toJson(fullVacancy.salary),
            fullVacancy.alternate_url,
            fullVacancy.brandedDescription,
            fullVacancy.contacts?.name,
            fullVacancy.contacts?.email,
            gson.toJson(fullVacancy.contacts?.phones),
            fullVacancy.description,
            fullVacancy.experience,
            fullVacancy.employment,
            fullVacancy.skills
        )
    }
    fun fromVacancyEntityToList(vacancies: List<VacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancy -> mapFromEntity(vacancy) }
    }

    private fun mapFromEntity(vacancyEntity: VacancyEntity): Vacancy {
        return Vacancy(
            vacancyEntity.id,
            vacancyEntity.name,
            vacancyEntity.city,
            vacancyEntity.employerName,
            0,
            vacancyEntity.employerLogoUrl,
            convertToSalary(vacancyEntity.salary),
        )
    }

    private fun convertToSalary(json: String?): Salary? {
        val myClassObject: Type = object : TypeToken<Salary>() {}.type
        return if (!json.isNullOrEmpty()) gson.fromJson(json, myClassObject) else null
    }

    fun fromVacancyEntityToFullVacancy(vacancy: VacancyEntity): FullVacancy {
        return  FullVacancy(
            vacancy.id,
            vacancy.name,
            vacancy.city,
            vacancy.employerName,
            vacancy.employerLogoUrl,
            mapToSalary(vacancy.salary),
            vacancy.alternate_url,
            vacancy.brandedDescription,
            mapToContacts(vacancy),
            vacancy.description,
            vacancy.experience,
            vacancy.employment,
            vacancy.skills
        )

    }

    private fun mapToSalary(json: String?): Salary? {
        return gson.fromJson(json,Salary::class.java)
    }

    private fun mapToContacts(vacancy: VacancyEntity): Contact {
        val listOfMyClassObject: Type = object : TypeToken<ArrayList<Phone>?>() {}.type
        val phones: List<Phone> = if (!vacancy.contactPhones.isNullOrEmpty()) gson.fromJson(vacancy.contactPhones, listOfMyClassObject) else mutableListOf()
        return Contact(
            vacancy.contactName,
            vacancy.contactEmail,
            phones
        )
    }
}