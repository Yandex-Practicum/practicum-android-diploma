package ru.practicum.android.diploma.domain

import ru.practicum.android.diploma.data.SomeSource

class SomeRepository(private val someSource: SomeSource) {
    fun getBusinessLogicData(): String {
        // Здесь могла бы быть сложная логика
        return "Обработанные данные: ${someSource.fetchData()}"
    }
}
