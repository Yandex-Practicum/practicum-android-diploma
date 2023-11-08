package ru.practicum.android.diploma.data.detail

import ru.practicum.android.diploma.data.dto.getFullVacancy
import ru.practicum.android.diploma.domain.detail.DetailRepository
import ru.practicum.android.diploma.domain.models.detail.Vacancy

class DetailRepositoryImpl(private val mapper: Mapper) : DetailRepository {
    override fun getVavancy(): FullVacancy {
        val vacancy = getFullVacancy()?.let { mapper.toFullVacancy(it) }
        return vacancy!!
    }
}