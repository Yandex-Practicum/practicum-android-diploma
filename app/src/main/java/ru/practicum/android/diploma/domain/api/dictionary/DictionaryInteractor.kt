package ru.practicum.android.diploma.domain.api.dictionary

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Currency

interface DictionaryInteractor {
    fun getCurrencyDictionary():Map<String,Currency>
    suspend fun getCurrency(code:String):Currency
}
