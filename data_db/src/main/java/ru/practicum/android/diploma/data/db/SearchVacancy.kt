package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_vacancies_table")
data class SearchVacancy(
    // для справки
// https://api.hh.ru/openapi/redoc#tag/Obshie-spravochniki/operation/get-dictionaries
// всё поля по исходному полю из ответа
    @PrimaryKey val code: String, // по справочнику может быть null, но т.к. это указано
    // на всех полях - решил что они просто страхуются и сделал нашим key
    val areas: Array<String?>?, // внесем через конвертер
    val addressCity: String?,
    val currencyName: String?,
    val description: String?, // включает Обязанности, Требования, Условия с макета (всё один текст)
    val employer: String?,
    val employment: String?, // Тип занятости
    val experience: String?,
    val keySkills: Array<String?>?, // внесем через конвертер
    val industry: String?,
    val name: String?, // название вакансии
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryGross: Boolean?,
)

