package ru.practicum.android.diploma.common.util

import androidx.room.PrimaryKey
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import java.time.Instant

class Converter {
   // @TypeConverter
    fun map(vacancy: VacancyItems): VacancyEntity {
        var salary : String? = null
        salary = vacancy.salary?.getSalaryToString()
        return VacancyEntity(
            vacancy.id, vacancy.name, vacancy.areaName, vacancy.employer,
            vacancy.iconUrl, salary, Instant.now().getEpochSecond()
        )
    }

    fun map(vacancy: VacancyEntity): VacancyItems {
        return VacancyItems(
            vacancy.id, vacancy.name, vacancy.areaName, vacancy.employer,
            vacancy.iconUrl, vacancy.salary
        )
    }
}

/*data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String, // Название вакансии
    val areaName: String, // Город
    val employer: String, // Компания
    val iconUrl: String? = null, // Иконка компании
    val salary: String? = null,//Salary? = null, // Зарплатка в наносекунду)
    var timeOfLikeSec: Long // время добавления в избранное (в секундах от начала эпохи UNIX - 1970-01-01 00:00:00 UTC)
)

data class VacancyItems(
    val id: String,
    val name: String, // Название вакансии
    val areaName: String, // Город
    val employer: String, // Компания
    val iconUrl: String? = null, // Иконка компании
    val salary: Salary? = null, // Зарплатка в наносекунду)
)

 */
