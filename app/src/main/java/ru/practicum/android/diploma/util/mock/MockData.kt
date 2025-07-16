package ru.practicum.android.diploma.util.mock

import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

object MockData {

    val testVacancies = listOf(
        FavoriteVacancyEntity(
            id = "1",
            name = "Android-разработчик",
            employerName = "Яндекс",
            areaName = "Москва",
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
            currency = "RUR",
            experience = "От 1 года до 3 лет",
            employment = "Полная занятость",
            schedule = "Полный день",
            keySkills = "Kotlin,Android,Jetpack,Coroutines,Dagger",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Yandex_icon.svg/1200px-Yandex_icon.svg.png",
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
            currency = "RUR",
            experience = "3–6 лет",
            employment = "Полная занятость",
            schedule = "Удалёнка",
            keySkills = "Kotlin,Spring,PostgreSQL,Kafka,Highload",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/VK_Compact_Logo_%282021-present%29.svg/2048px-VK_Compact_Logo_%282021-present%29.svg.png",
            url = "https://hh.ru/vacancy/2"
        ),
        FavoriteVacancyEntity(
            id = "3",
            name = "Flutter-разработчик",
            employerName = "Ozon",
            areaName = "Иннополис",
            descriptionHtml = "<p>Разработка кросс-платформенных приложений для e-commerce. Мы ценим чистоту кода и внимание к деталям. Присоединяйся к нашей команде, чтобы создавать лучшие продукты для миллионов пользователей!</p>",
            salaryFrom = 120_000,
            salaryTo = null,
            currency = "RUR",
            experience = "От 1 года до 3 лет",
            employment = "Полная занятость",
            schedule = "Гибкий график",
            keySkills = "Flutter,Dart,Bloc,Firebase",
            logoUrl = null,
            url = "https://hh.ru/vacancy/3"
        ),
        FavoriteVacancyEntity(
            id = "4",
            name = "Data Scientist",
            employerName = "Sber",
            areaName = "Москва",
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
            currency = "RUR",
            experience = "От 3 до 6 лет",
            employment = "Полная занятость",
            schedule = "Полный день",
            keySkills = "Python,Pandas,Scikit-learn,SQL,Machine Learning",
            logoUrl = "https://habrastorage.org/getpro/moikrug/uploads/company/100/006/341/2/logo/big_32156f1572916e1f7fb432e67e1defc2.png",
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
            currency = "RUR",
            experience = "От 1 года до 3 лет",
            employment = "Полная занятость",
            schedule = "Удаленная работа",
            keySkills = "Тестирование,Postman,SQL,Jira,Тест-кейсы",
            logoUrl = "https://www.m24.ru/b/d/nBkSUhL2hFkvkciyI76BrNOp2Z318Ji-mijFnuWR9mOBdDebBizCnTY8qdJf6ReJ58vU9meMMok3Ee2nhSR6ISeO9G1N_wjJ=zOF_5VePNY4jUG1rQIo8_g.jpg",
            url = "https://hh.ru/vacancy/5"
        )
    )

    fun getVacancyById(id: String): FavoriteVacancyEntity? {
        return testVacancies.find { it.id == id }
    }
}
