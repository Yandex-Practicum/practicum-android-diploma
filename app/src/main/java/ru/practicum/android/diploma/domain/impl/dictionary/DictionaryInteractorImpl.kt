package ru.practicum.android.diploma.domain.impl.dictionary

import ru.practicum.android.diploma.domain.api.dictionary.CurrencyRepository
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryRepository
import ru.practicum.android.diploma.domain.models.Currency

class DictionaryInteractorImpl(
    private val currencyRepository: CurrencyRepository,
    private val dictionaryRepository: DictionaryRepository
) : DictionaryInteractor {
    override suspend fun getCurrencyDictionary(): Map<String, Currency> {
        val dbDictionary = mutableListOf<Currency>()
        dictionaryRepository.getCurrencyDictionary().collect {
            dbDictionary.addAll(it)
        }
        if (dbDictionary.isNullOrEmpty()) {
            currencyRepository.getCurrencyDictionary().collect {
                dbDictionary.addAll(it)
            }
        }
        dictionaryRepository.addCurrencies(dbDictionary)
        return dbDictionary.associateBy({ it.code }, { it })
    }

    override suspend fun getCurrency(code: String): Currency {
        var currency = dictionaryRepository.getCurrency(code)
        if (currency == null) {
            currencyRepository.getCurrencyDictionary().collect {
                dictionaryRepository.addCurrencies(it)
            }
            currency = dictionaryRepository.getCurrency(code)
        }
        return currency ?: Currency(code, "", "")
    }
}
