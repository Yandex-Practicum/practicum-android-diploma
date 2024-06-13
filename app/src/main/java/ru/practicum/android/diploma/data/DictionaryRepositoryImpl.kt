package ru.practicum.android.diploma.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converters.CurrencyConverter
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryRepository
import ru.practicum.android.diploma.domain.models.Currency
import java.io.IOException

class DictionaryRepositoryImpl(private val appDatabase: AppDatabase, private val currencyConverter: CurrencyConverter) :
    DictionaryRepository {
    override suspend fun getCurrency(code: String): Currency? {
        return try {
            currencyConverter.map(appDatabase.dictionaryDAO().getCurrencyByCode(code))
        } catch (e: IOException) {
            Log.e("DATABASE EMPTY", e.toString())
            null
        }
    }

    override fun getCurrencyDictionary(): Flow<List<Currency>> = flow {
        emit(appDatabase.dictionaryDAO().getCurrencyDictionary().map { currencyConverter.map(it) })
    }

    override suspend fun addCurrencies(currencies: List<Currency>) {
        appDatabase.dictionaryDAO().addCurrencies(currencies.map { currencyConverter.map(it) })
    }
}
