package ru.practicum.android.diploma.core.domain.mocks

import ru.practicum.android.diploma.core.domain.models.Vacancy

private fun Vacancy.Companion.mock1(): Vacancy {
    return Vacancy(
        id = "1",
        name = "Android-разработчик, Москва",
        employerName = "Еда",
        employerLogoUrl = "",
        salary = "от 100 000 ₽",
    )
}

private fun Vacancy.Companion.mock2(): Vacancy {
    return Vacancy(
        id = "2",
        name = "Разработчик на С++ в команду внутренних сервисов, Москва",
        employerName = "Авто.ру",
        employerLogoUrl = "https://avatars.mds.yandex.net/get-lpc/9736426/85897289-cea3-43c3-9179-e7f9602b4552/width_480x2_q80",
        salary = "от 1 000 до 1 500 €",
    )
}

fun Vacancy.Companion.mockList(): List<Vacancy> {
    return listOf(Vacancy.mock1(), Vacancy.mock2())
}
