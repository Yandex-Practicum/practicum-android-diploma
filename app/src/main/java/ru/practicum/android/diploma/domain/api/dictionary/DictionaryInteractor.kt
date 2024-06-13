package ru.practicum.android.diploma.domain.api.dictionary

import ru.practicum.android.diploma.domain.models.Currency

interface DictionaryInteractor {
    suspend fun getCurrencyDictionary(): Map<String, Currency>
    suspend fun getCurrency(code: String): Currency
}
