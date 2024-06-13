package ru.practicum.android.diploma.data.db.converters

import ru.practicum.android.diploma.data.db.dao.models.CurrencyDictionaryEntity
import ru.practicum.android.diploma.data.dto.CurrencyDTO
import ru.practicum.android.diploma.domain.models.Currency

class CurrencyConverter {
    fun map(currency: Currency): CurrencyDictionaryEntity = CurrencyDictionaryEntity(
        code = currency.code,
        name = currency.name,
        abbr = currency.abbr
    )

    fun map(currencyDictionaryEntity: CurrencyDictionaryEntity): Currency = Currency(
        code = currencyDictionaryEntity.code,
        name = currencyDictionaryEntity.name,
        abbr = currencyDictionaryEntity.abbr
    )

    fun map(currencyDTO: CurrencyDTO): Currency = Currency(
        code = currencyDTO.code,
        name = currencyDTO.name,
        abbr = currencyDTO.abbr
    )
}
