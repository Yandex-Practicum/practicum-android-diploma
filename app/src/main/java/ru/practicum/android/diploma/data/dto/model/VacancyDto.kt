package ru.practicum.android.diploma.data.dto.model

import java.io.Serializable

data class VacancyDto(
    val id: String,
    val area: String,
    val text: String,
    val searchField: String,
    // т.к. вроде в фильтрации нигде не используется дальше, поэтому оставил стринг просто для вывода
    val experience: String,
    val employment: String,// Тип занятости - напр. полная
    val schedule: String,
    val industry: Int,// искать надо по id (Int), но хранить наверное уже лучше в тексте
    val salary: Int,
    val employer: String,
    val onlyWithSalary: Boolean
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
