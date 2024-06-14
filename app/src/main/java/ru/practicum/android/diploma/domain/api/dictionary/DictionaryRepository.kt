package ru.practicum.android.diploma.domain.api.dictionary

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Currency

interface DictionaryRepository {
    suspend fun getCurrency(code: String): Currency?
    fun getCurrencyDictionary(): Flow<List<Currency>>
    suspend fun addCurrencies(currencies: List<Currency>)
}
