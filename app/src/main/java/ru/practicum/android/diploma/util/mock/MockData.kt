package ru.practicum.android.diploma.util.mock

import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

object MockData {

    // --- Константы для повторяющихся строк (решение для StringLiteralDuplication) ---
    private const val CURRENCY_RUR = "RUR"
    private const val AREA_MOSCOW = "Москва"
    private const val EXPERIENCE_1_TO_3_YEARS = "От 1 года до 3 лет"
    private const val EMPLOYMENT_FULL = "Полная занятость"
    private const val SCHEDULE_FULL_DAY = "Полный день"

    // --- Константы для длинных URL ---
    private const val URL_YANDEX_LOGO =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Yandex_icon.svg/1200px-Yandex_icon.svg.png"
    private const val URL_VK_LOGO =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/" +
            "VK_Compact_Logo_%282021-present%29.svg/2048px-VK_Compact_Logo_%282021-present%29.svg.png"
    private const val URL_SBER_LOGO = "https://habrastorage.org/getpro/" +
        "moikrug/uploads/company/100/006/341/2/logo/big_32156f1572916e1f7fb432e67e1defc2.png"
    private const val URL_TINKOFF_LOGO = "https://www.m24.ru/b/d/" +
        "nBkSUhL2hFkvkciyI76BrNOp2Z318Ji-mijFnuWR9mOBdDebBizCnTY8" +
        "qdJf6ReJ58vU9meMMok3Ee2nhSR6ISeO9G1N_wjJ=zOF_5VePNY4jUG1rQIo8_g.jpg"

    val testVacancies = listOf(
        FavoriteVacancyEntity(
            id = "1",
            name = "Android-разработчик",
            employerName = "Яндекс",
            areaName = AREA_MOSCOW,
            descriptionHtml = """
                <p><strong>Обязанности:</strong></p>
                <ul>
                <li>Разработка и поддержка мобильных приложений на Android.</li>
                <li>Взаимодействие с командой дизайнеров и тестировщиков.</li>
                <li>Оптимизация производительности и улучшение пользовательского опыта.</li>
                </ul>
                <p><strong>Требования:</strong></p>
                <ul>
                <li>Опыт разработки на Kotlin от 2 лет.</li>
                <li>Знание Android SDK, Jetpack Compose.</li>
                <li>Понимание принципов SOLID, Clean Architecture.</li>
                </ul>
                <p><strong>Условия:</strong></p>
                <ul>
                <li>Конкурентная заработная плата.</li>
                <li>ДМС со стоматологией.</li>
                <li>Возможность работать удаленно или в гибридном формате.</li>
                </ul>
            """.trimIndent(),
            salaryFrom = 150_000,
            salaryTo = 200_000,
            currency = CURRENCY_RUR,
            experience = EXPERIENCE_1_TO_3_YEARS,
            employment = EMPLOYMENT_FULL,
            schedule = SCHEDULE_FULL_DAY,
            keySkills = "Kotlin,Android,Jetpack,Coroutines,Dagger",
            logoUrl = URL_YANDEX_LOGO,
            url = "https://hh.ru/vacancy/1"
        ),
        FavoriteVacancyEntity(
            id = "2",
            name = "Backend-разработчик",
            employerName = "VK",
            areaName = "Санкт-Петербург",
            descriptionHtml = """
                <h4>Чем предстоит заниматься:</h4>
                <p>Разработка высоконагруженных микросервисов для социальных сетей.</p>
                <br>
                <h4>Наш стек:</h4>
                <ul><li>Kotlin, Java</li><li>Spring Framework (Boot, Cloud, Data)</li><li>PostgreSQL, Redis, Kafka</li><li>Kubernetes, Docker</li></ul>
                <p><em>Мы ищем сильного специалиста, готового решать сложные и интересные задачи.</em></p>
            """.trimIndent(),
            salaryFrom = 180_000,
            salaryTo = 220_000,
            currency = CURRENCY_RUR,
            experience = "3–6 лет",
            employment = EMPLOYMENT_FULL,
            schedule = "Удалёнка",
            keySkills = "Kotlin,Spring,PostgreSQL,Kafka,Highload",
            logoUrl = URL_VK_LOGO,
            url = "https://hh.ru/vacancy/2"
        ),
        FavoriteVacancyEntity(
            id = "3",
            name = "Flutter-разработчик",
            employerName = "Ozon",
            areaName = "Иннополис",
            descriptionHtml = "<p>Разработка кросс-платформенных приложений для e-commerce. " +
                "Мы ценим чистоту кода и внимание к деталям. Присоединяйся к нашей команде, " +
                "чтобы создавать лучшие продукты для миллионов пользователей!</p>",
            salaryFrom = 120_000,
            salaryTo = null,
            currency = CURRENCY_RUR,
            experience = EXPERIENCE_1_TO_3_YEARS,
            employment = EMPLOYMENT_FULL,
            schedule = "Гибкий график",
            keySkills = "Flutter,Dart,Bloc,Firebase",
            logoUrl = null,
            url = "https://hh.ru/vacancy/3"
        ),
        FavoriteVacancyEntity(
            id = "4",
            name = "Data Scientist",
            employerName = "Sber",
            areaName = AREA_MOSCOW,
            descriptionHtml = """
                <h3>Присоединяйся к команде AI в Сбере!</h3>
                <p>Мы ищем Data Scientist'а для работы над проектами в области рекомендательных систем и предсказания оттока клиентов.</p>
                <h4>Задачи:</h4>
                <ol>
                    <li>Проведение исследований и построение ML-моделей.</li>
                    <li>Разработка и внедрение моделей в production.</li>
                    <li>Анализ эффективности моделей и их дальнейшая доработка.</li>
                </ol>
                <h4>Ключевые технологии:</h4>
                <p>Python, Pandas, Scikit-learn, PyTorch, SQL.</p>
            """.trimIndent(),
            salaryFrom = 200_000,
            salaryTo = 280_000,
            currency = CURRENCY_RUR,
            experience = "От 3 до 6 лет",
            employment = EMPLOYMENT_FULL,
            schedule = SCHEDULE_FULL_DAY,
            keySkills = "Python,Pandas,Scikit-learn,SQL,Machine Learning",
            logoUrl = URL_SBER_LOGO,
            url = "https://hh.ru/vacancy/4"
        ),
        FavoriteVacancyEntity(
            id = "5",
            name = "QA Engineer",
            employerName = "Tinkoff",
            areaName = "Удаленно",
            descriptionHtml = """
                <p>Ищем внимательного и ответственного инженера по обеспечению качества для работы над нашими мобильными банковскими приложениями.</p>
                <p><strong>Что нужно будет делать:</strong></p>
                <ul>
                    <li>Ручное тестирование мобильных приложений (iOS, Android).</li>
                    <li>Написание и поддержка тестовой документации (тест-кейсы, чек-листы).</li>
                    <li>Взаимодействие с разработчиками и аналитиками.</li>
                    <li>Автоматизация тестирования (будет плюсом).</li>
                </ul>
            """.trimIndent(),
            salaryFrom = null,
            salaryTo = 150_000,
            currency = CURRENCY_RUR,
            experience = EXPERIENCE_1_TO_3_YEARS,
            employment = EMPLOYMENT_FULL,
            schedule = "Удаленная работа",
            keySkills = "Тестирование,Postman,SQL,Jira,Тест-кейсы",
            logoUrl = URL_TINKOFF_LOGO,
            url = "https://hh.ru/vacancy/5"
        )
    )

    /**
     * Возвращает тестовую вакансию по её ID.
     * @param id Идентификатор вакансии.
     * @return [FavoriteVacancyEntity] или null, если вакансия не найдена.
     */
    fun getVacancyById(id: String): FavoriteVacancyEntity? {
        return testVacancies.find { it.id == id }
    }
}
