package ru.practicum.android.diploma.search.presentation.list_items

import ru.practicum.android.diploma.search.domain.model.VacancyItems

class Mapper {
    companion object {
        fun map(vacancyItems: VacancyItems): ListItem.Vacancy {
            return with(vacancyItems) {
                ListItem.Vacancy(
                    id,
                    name,
                    areaName,
                    employer,
                    iconUrl,
                    salary
                )
            }
        }
    }
}

