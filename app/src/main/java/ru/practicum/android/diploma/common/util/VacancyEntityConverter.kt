package ru.practicum.android.diploma.common.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import java.time.Instant

//@ProvidedTypeConverter
class VacancyEntityConverter {
   @TypeConverter
    fun map(vacancy: VacancyItems): VacancyEntity {
        var salary : String? = null
        salary = vacancy.salary?.getSalaryToString()
        return VacancyEntity(
            vacancy.id, vacancy.name, vacancy.areaName, vacancy.employer,
            vacancy.iconUrl, salary, Instant.now().getEpochSecond()
        )
    }

    @TypeConverter
    fun map(vacancy: VacancyEntity): VacancyItems {
        var from: Int? = null
        var to: Int? = null
        var currency: String? = null
        if (!vacancy.salary.isNullOrBlank() && (vacancy.salary != "Зарплата не указана")){
            var strList = vacancy.salary.split(' ')
            val count = strList.count()

            var index = strList.indexOfFirst{ it == "от" }
            if ((index != -1) && (index < count-1)) from = strList[index + 1].toInt()

            index = strList.indexOfFirst{ it == "до" }
            if ((index != -1) && (index < count-1)) to = strList[index + 1].toInt()

            currency = formatCurrencyReverse(strList.last())
            return VacancyItems(
                vacancy.id, vacancy.name, vacancy.areaName, vacancy.employer,
                vacancy.iconUrl, Salary(from,to,currency)
            )
        }
        return VacancyItems(
            vacancy.id, vacancy.name, vacancy.areaName, vacancy.employer,
            vacancy.iconUrl, null
        )
    }

    private fun formatCurrencyReverse(abbr: String): String? {
        if (abbr == "") return null
        return when (abbr) {
            "₼" -> "AZN"
            "Br" -> "BYR"
            "€" -> "EUR"
            "₾" -> "GEL"
            "С" -> "KGS"
            "₸" -> "KZT"
            "₽" -> "RUR"
            "₴" -> "UAH"
            "$" -> "USD"
            "UZS" -> "UZS"
            else -> abbr
        }
    }
}
