package ru.practicum.android.diploma.details.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.domain.DetailsInteractor
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyFullInfoModel
import ru.practicum.android.diploma.search.domain.models.assistants.Contacts
import ru.practicum.android.diploma.search.domain.models.assistants.Employment
import ru.practicum.android.diploma.search.domain.models.assistants.Experience
import ru.practicum.android.diploma.search.domain.models.assistants.KeySkill
import ru.practicum.android.diploma.search.domain.models.assistants.Phone
import ru.practicum.android.diploma.search.domain.models.assistants.Schedule
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

/** ViewModel для экрана деталей вакансии */
class DetailsViewModel @Inject constructor(
    val logger: Logger,
    private val detailsInteractor: DetailsInteractor
) : BaseViewModel(logger) {

    private val uiStateMutable = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Empty)
    val uiState: StateFlow<DetailsScreenState> = uiStateMutable.asStateFlow()

    /** Добавление вакансии в избранное */
    fun addToFavorites(vacancy: Vacancy) {
        viewModelScope.launch(Dispatchers.IO) {
            log(thisName, "addToFavorites   }")
            detailsInteractor.addVacancyToFavorites(vacancy).collect {
                log(thisName, "id inserted= $it")
            }
        }
    }

    /** Удаление вакансии из избранного */
    fun deleteVacancy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.removeVacancyFromFavorite(id).collect {
                log(thisName, "$id was removed")
            }
        }
    }

    /** Удаление вакансии из избранного */
    fun removeFromFavorites(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            log(thisName, "removeFromFavorites   }")
        }
    }


    /** Получение вакансии по ID. Пока что моковые данные без запроса к серверу */
    fun getVacancyByID(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            logger.log(thisName, "getVacancyByID()")
            /*detailsInteractor.getFullVacancyInfo(id).collect {

            }*/
            uiStateMutable.emit(DetailsScreenState.Content(mokData()))
        }
    }


    /** Моковые данные. После подключения репозитория будут удалены */
    private fun mokData(): VacancyFullInfoModel {
        return VacancyFullInfoModel(
            id = "85773787",
            experience = Experience(
                id = "between1And3",
                name = "От 1 года до 3 лет"
            ),
            employment = Employment(
                id = "full",
                name = "Полная занятость"
            ),
            schedule = Schedule(
                id = "fullDay",
                name = "Полный день"
            ),
            description = "<p>Playrix является крупнейшей игровой компанией в Европе и входит в топ-3 самых успешных мобильных разработчиков в мире. Мы создали такие хиты, как Gardenscapes, Fishdom, Manor Matters, Homescapes, Wildscapes и Township, которые скачали более 2 миллиардов игроков. В игры Playrix каждый месяц играют более 100 миллионов человек.</p> <p>Команда саппорта Playrix ищет продакт-менеджера по клиентскому опыту.</p> <p>Вам предстоит анализировать метрики саппорта, придумывать, как можно улучшить процессы и жизнь нашего пользователя, защищать и реализовывать свои проекты.</p> <p>От нас — свобода в решениях, ресурсы и сильная операционная команда. От вас — опыт работы с проектами и желание постоянно улучшать клиентский сервис.</p> <p>Лидируя проекты в тесной связке с другими командами саппорта, ты сможешь влиять на развитие игры и делать миллионы наших игроков счастливее.</p> <p><em>Эта вакансия предполагает возможность работы в одном из наших офисов на Кипре, Сербии, Черногории, Армении, Казахстане либо удаленно.</em></p> <p><strong>Наши задачи:</strong></p> <ul> <li> <p>выстраивание новых процессов работы с обращениями пользователей и оптимизация текущих;</p> </li> <li> <p>работа с аналитикой операционных и ключевых показателей команды;</p> </li> <li> <p>запуск и улучшение инструментов саппорта: тикетные системы, AI, боты, статистика и т. д.;</p> </li> <li> <p>работа с метриками NPS, CES, CSAT, составление CJM и проведение пользовательских интервью;</p> </li> <li> <p>эксперименты/проекты для улучшения клиентского опыта;</p> </li> <li> <p>активное участие в стратегическом планировании команды саппорта.</p> </li> </ul> <p><strong>Мы ожидаем, что вы:</strong></p> <ul> <li> <p>работали в клиентском сервисе не менее 3 лет в крупных геймдев / IT компаниях;</p> </li> <li> <p>знаете английский язык на уровне не ниже B2 (как письменный, так и устный);</p> </li> <li> <p>имеете опыт работы с внешними тикетными системами и другими партнерами;</p> </li> <li> <p>работали с проектами: умеете планировать проект, разбивать его на блоки и задачи, расставлять приоритеты и решать задачи в срок в быстро меняющихся условиях;</p> </li> <li> <p>можете принимать решения как на основе большого объёма данных, так и при полном их отсутствии;</p> </li> <li> <p>обладаете развитыми коммуникативными навыками и умеете отстаивать свою позицию как с внешними партнерами, так и агентами или продакт директорами внутри компании.</p> </li> <li> <p>проактивны и самостоятельны в изменениях существующей логики или процессов;</p> </li> <li> <p>способны работать в условиях многозадачности (несколько разноплановых проектов одновременно), готовы к высокой скорости работы, быстро адаптируетесь к условиям постоянных изменений и роста продукта.</p> </li> </ul> <p><strong>Мы предлагаем</strong>:</p> <p><strong>Комфортные условия</strong>. Работайте в офисе, удаленно и в гибридном формате. У нас есть все для комфортной работы, где бы вы не находились.</p> <p><strong>Забота о здоровье</strong>. Компенсируем онлайн-сессии с психологом, открываем для вас и ваших детей ДМС со стоматологией и лечением от COVID-19.</p> <p><strong>Забота о благополучии</strong>. Сохраняем 100% зарплату во время отпуска или больничного без лишних справок. А для особых случаев предоставим дополнительные выходные.</p> <p><strong>Развитие и обучение</strong>. Оплачиваем участие в профильных конференциях и курсах, регулярно проводим внутренние буткемпы и предлагаем скидки на курсы английского языка.</p> <p><strong>Развлечения и мерч</strong>. Конкурсы, спортивные челленджи, вечеринки, хакатоны и внутренние офлайн-ивенты для команд — каждый год мы проводим сотни мероприятий по всему миру.</p> <p><strong>Спорт и фитнес</strong>. Поддерживаем здоровый образ жизни и компенсируем покупку любых спортивных абонементов и подписки на фитнес-приложения.</p> <p><strong>Социальные проекты</strong>. Запускаем благотворительные проекты и поддерживаем идеи сотрудников в конкурсе грантов.</p>",
            keySkills = listOf(
                KeySkill(
                    name = "Разработка инструкций"
                ),
                KeySkill(
                    name = "Разработка технических заданий"
                ),
                KeySkill(
                    name = "Работа в команде"
                ),
                KeySkill(
                    name = "MS Visio"
                )
            ),
            contacts = Contacts(
                email = "user@example.com",
                name = "Виктория Иванова",
                phones = listOf(
                    Phone(
                        comment = null,
                        number = "000-00-00",
                        city = "985",
                        country = "7"
                    )
                )
            )
        )
    }

}