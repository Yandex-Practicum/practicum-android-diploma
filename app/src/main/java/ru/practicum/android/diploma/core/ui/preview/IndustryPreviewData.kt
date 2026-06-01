package ru.practicum.android.diploma.core.ui.preview

import ru.practicum.android.diploma.core.domain.models.Industry

fun Industry.Companion.mock1(): Industry {
    return Industry(
        id = "100",
        name = "Легкая промышленность",
    )
}

fun Industry.Companion.mock2(): List<Industry> {
    return listOf(
        Industry("1", "Авиаперевозки"),
        Industry("2", "Авиационная, вертолетная и аэрокосмическая промышленность"),
        Industry("3", "Автокомпоненты, запчасти (производство)"),
        Industry("4", "Автокомпоненты, запчасти, шины (продвеждение, оптовая торговля)"),
        Industry("5", "Автомобильные перевозки"),
        Industry("6", "Автошкола"),
        Industry("7", "Агентские услуги в недвижимости"),
        Industry("8", "Агрохимия (продвежение, оптовая торговля)"),
        Industry("9", "Агрохимия (производство)"),
        Industry("10", "Алкогольные напитки (продвижение, оптовая торговля)")
    )
}
