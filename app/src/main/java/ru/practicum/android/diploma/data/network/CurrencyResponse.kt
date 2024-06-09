package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.CurrencyDTO

data class CurrencyResponse(val currency: List<CurrencyDTO>?) : Response()
