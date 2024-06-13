package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.converters.CurrencyConverter
import ru.practicum.android.diploma.data.dto.CurrencyRequest
import ru.practicum.android.diploma.data.dto.CurrencyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.dictionary.CurrencyRepository
import ru.practicum.android.diploma.domain.models.Currency

class CurrencyRepositoryImpl(
    private val client: NetworkClient,
    private val converter: CurrencyConverter
) : CurrencyRepository {

    override fun getCurrencyDictionary(): Flow<List<Currency>> = flow {
        val response = client.doRequest(CurrencyRequest())
        when (response.resultCode) {
            CLIENT_SUCCESS_RESULT_CODE -> {
                val list = (response as CurrencyResponse).currency
                if (list == null) {
                    emit(listOf())
                } else {
                    emit(list.map { converter.map(it) })
                }
            }

            else -> {
                emit(listOf())
            }
        }
    }

    companion object {
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
