package ru.practicum.android.diploma.data.vacancydetail.dto.responseunits

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.responseUnits.Employer
import ru.practicum.android.diploma.data.dto.responseUnits.Salary
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyArea
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyType

class VacancyDetailDto(
    val id: String,
    val name: String,
    val area: VacancyArea, // Регион
    val contacts: Contacts?, // Контакты
    val employer: Employer, // Работодатель
    val salary: Salary?, // Зарплата
    val schedule: Schedule?, // График работы
    val type: VacancyType, // Тип вакансии
    val employment: Employment?, // Тип занятости
    val experience: Experience?, // Опыт работы
    val snippet: Snippet, // Дополнительные текстовые отрывки
    @SerializedName("key_skills")
    val keySkills: List<KeySkillVacancyDetail>, // Список ключевых навыков
    @SerializedName("alternate_url")
    val url: String, // Ссылка на представление вакансии на сайте
    val description: String, // Описание в html, не менее 200 символов
)
