package ru.practicum.android.diploma.vacancy.ui.mock

import ru.practicum.android.diploma.vacancy.domain.models.Contacts
import ru.practicum.android.diploma.vacancy.domain.models.Phone
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

internal object VacancyDetailsPreview {

    private const val DESCRIPTION_FULL =
        "Обязанности:\n" +
            "• Разрабатывать новую функциональность приложения\n" +
            "• Помогать с интеграцией нашего SDK в другие приложения\n" +
            "• Проектировать большие новые модули\n" +
            "• Писать UI- и unit-тесты\n" +
            "• Следить за работоспособностью сервиса и устранять технический долг\n\n" +
            "Требования:\n" +
            "• 100% Kotlin\n" +
            "• WebRTC\n" +
            "• CI по модели Trunk Based Development\n\n" +
            "Условия:\n" +
            "• сильная команда, с которой можно расти\n" +
            "• возможность влиять на процесс и результат\n" +
            "• расширенная программа ДМС: стоматология, обследования, вызов врача на дом"

    val full: VacancyDetails = VacancyDetails(
        id = "1",
        name = "Android-разработчик",
        salary = "от 100 000 ₽",
        employerName = "Еда",
        employerLogoUrl = null,
        city = "Москва",
        address = "Москва, Льва Толстого, 16",
        areaName = "Москва",
        experience = "От 1 года до 3 лет",
        schedule = "Удалённая работа",
        employment = "Полная занятость",
        description = DESCRIPTION_FULL,
        keySkills = listOf(
            "Знание классических алгоритмов и структур данных",
            "Программирование для Android больше одного года",
            "Знание WebRTC"
        ),
        contacts = Contacts(
            name = "Иван Иванов",
            email = "hr@eda.example",
            phones = listOf(
                Phone(number = "+7 (495) 123-45-67", comment = "Звонить с 10:00 до 19:00"),
                Phone(number = "+7 (495) 765-43-21", comment = null)
            )
        ),
        alternateUrl = "https://hh.ru/vacancy/1"
    )

    val minimal: VacancyDetails = VacancyDetails(
        id = "2",
        name = "Junior Android Developer",
        salary = "Уровень зарплаты не указан",
        employerName = "Авто.ру",
        employerLogoUrl = null,
        city = null,
        address = null,
        areaName = "Санкт-Петербург",
        experience = null,
        schedule = null,
        employment = null,
        description = "Ищем младшего разработчика в команду внутренних сервисов.",
        keySkills = emptyList(),
        contacts = null,
        alternateUrl = "https://hh.ru/vacancy/2"
    )

    val withLogo: VacancyDetails = full.copy(
        id = "3",
        employerLogoUrl =
        "https://avatars.mds.yandex.net/get-lpc/9736426/85897289-cea3-43c3-9179-e7f9602b4552/" +
            "width_480x2_q80"
    )

    val all: List<VacancyDetails> = listOf(full, minimal, withLogo)
}
