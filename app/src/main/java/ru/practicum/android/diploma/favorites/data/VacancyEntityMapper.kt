package ru.practicum.android.diploma.favorites.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.favorites.domain.entity.VacancyFavorite
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import java.time.Instant

class VacancyEntityMapper {
    @TypeConverter
    fun convertToVacancyItems(vacancy: VacancyEntity): VacancyItems {
        return VacancyItems(
            id = vacancy.id,
            name = vacancy.name,
            salary = convertStringToSalary(vacancy.salary),
            areaName = vacancy.area ?: "",
            employer = vacancy.employment ?: "",
            iconUrl = vacancy.logoLink ?: ""
        )
    }
    @TypeConverter
    fun map(vacancyEntity: VacancyEntity?): VacancyFavorite? {
        if (vacancyEntity == null) {
            return null
        }
        return VacancyFavorite(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            salary = convertStringToSalary(vacancyEntity.salary),
            companyLogo = vacancyEntity.logoLink,
            companyName = vacancyEntity.companyName,
            area = vacancyEntity.area,
            address = vacancyEntity.address,
            experience = vacancyEntity.experience,
            schedule = vacancyEntity.schedule,
            employment = vacancyEntity.employment,
            description = vacancyEntity.description,
            keySkills = vacancyEntity.keySkill,
            vacancyUrl = vacancyEntity.vacancyUrl,
        )
    }
    @TypeConverter
    fun map(vacancy: VacancyFavorite): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            companyName = vacancy.name,
            salary = convertSalaryToString(vacancy.salary),
            area = vacancy.area,
            address = vacancy.address,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            description = vacancy.description,
            keySkill = vacancy.keySkills,
            vacancyUrl = vacancy.vacancyUrl,
            logoLink = vacancy.companyLogo,
            timeOfLikeSec = Instant.now().epochSecond
        )
    }

    private fun convertSalaryToString(salary: Salary?): String? {
        val gson = Gson()
        return gson.toJson(salary, Salary::class.java)
    }

    private fun convertStringToSalary(salary: String?): Salary? {
        val gson = Gson()
        return gson.fromJson(salary, Salary::class.java)
    }
}
