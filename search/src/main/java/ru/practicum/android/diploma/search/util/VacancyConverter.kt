package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.data.networkclient.api.dto.Item
import ru.practicum.android.diploma.search.domain.Vacancy

class VacancyConverter {
    fun map(items: List<Item>): List<Vacancy> {
        return ArrayList(items.map {
            with(it) {
                Vacancy(
                    name,
                    employer.name,
                    "${salary.from} ${salary.to}",
                    salary.currency,
                    employer.logoUrls.original
                )
            }
        })
    }
}
