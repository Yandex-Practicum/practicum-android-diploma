package ru.practicum.android.diploma.data.dto.responses.vacancy.list

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.responses.vacancy.Area
import ru.practicum.android.diploma.data.dto.responses.vacancy.Employer
import ru.practicum.android.diploma.data.dto.responses.vacancy.Salary

data class Item(
    val id: String, // идентификатор вакансии
    val area: Area, // внутри название город
    val employer: Employer, // внутри информация об организации и лого
    val name: String, // название вакансии
    @SerializedName("published_at")
    val publishedAt: String, // дата публикации в формате "2013-10-11T13:27:16+0400"
    val salary: Salary, // внутри з/п
    @SerializedName("show_logo_in_search")
    val showLogo: Boolean, // показывать ли логотип при поиске
)