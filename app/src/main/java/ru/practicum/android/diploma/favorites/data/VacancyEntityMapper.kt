package ru.practicum.android.diploma.favorites.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.favorites.domain.entity.VacancyFavorite
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.EmploymentDto
import java.time.Instant

class VacancyEntityMapper {
    @TypeConverter
    fun convertToVacancyItems(vacancy: VacancyEntity): VacancyItems {
        return VacancyItems(
            id = vacancy.id,
            name = vacancy.name,
            salary = convertJsonToObject(vacancy.salary),
            areaName = getAreaName(vacancy),
            employer = getEmploymentName(vacancy),
            iconUrl = getLogoUrl(vacancy),
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
            employer = convertJsonToObject(vacancyEntity.employer),
            salary = convertJsonToObject(vacancyEntity.salary),
            area = convertJsonToObject(vacancyEntity.area),
            address = convertJsonToObject(vacancyEntity.address),
            experience = convertJsonToObject(vacancyEntity.experience),
            schedule = convertJsonToObject(vacancyEntity.schedule),
            employment = convertJsonToObject(vacancyEntity.employment),
            description = vacancyEntity.description,
            keySkills = convertJsonToObject(vacancyEntity.keySkills),
            vacancyUrl = vacancyEntity.vacancyUrl,
        )
    }
    @TypeConverter
    fun map(vacancy: VacancyFavorite): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            employer = convertObjectToJson(vacancy.employer),
            salary = convertObjectToJson(vacancy.salary),
            area = convertObjectToJson(vacancy.area),
            address = convertObjectToJson(vacancy.address),
            experience = convertObjectToJson(vacancy.experience),
            schedule = convertObjectToJson(vacancy.schedule),
            employment = convertObjectToJson(vacancy.employment),
            description = vacancy.description,
            keySkills = convertObjectToJson(vacancy.keySkills),
            vacancyUrl = vacancy.vacancyUrl,
            timeOfLikeSec = Instant.now().epochSecond
        )
    }

    // Json Конвертеры
    private inline fun <reified T> convertObjectToJson(obj: T?): String? {
        val gson = Gson()
        return gson.toJson(obj, T::class.java)
    }

    private inline fun <reified T> convertJsonToObject(json: String?): T? {
        val gson = Gson()
        return gson.fromJson(json, T::class.java)
    }

    // Получение строковых значений не VacancyItems
    private fun getAreaName(vacancy: VacancyEntity): String {
        val areaDto = convertJsonToObject<AreaDto>(vacancy.area)
        return areaDto?.name ?: ""
    }

    private fun getEmploymentName(vacancy: VacancyEntity): String {
        val employmentDto = convertJsonToObject<EmploymentDto>(vacancy.employment)
        return employmentDto?.name ?: ""
    }

    private fun getLogoUrl(vacancy: VacancyEntity): String {
        val employerDto = convertJsonToObject<EmployerDto>(vacancy.employer)
        return employerDto?.logoUrls?.iconBig ?: ""
    }
}
