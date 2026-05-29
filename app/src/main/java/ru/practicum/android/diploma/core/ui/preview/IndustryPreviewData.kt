package ru.practicum.android.diploma.core.ui.preview

import ru.practicum.android.diploma.core.domain.models.Industry

fun Industry.Companion.mock1(): Industry {
    return Industry(
        id = "100",
        name = "Легкая промышленность",
    )
}
