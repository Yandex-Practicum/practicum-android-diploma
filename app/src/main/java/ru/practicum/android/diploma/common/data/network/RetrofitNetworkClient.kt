package ru.practicum.android.diploma.common.data.network

import ru.practicum.android.diploma.common.util.ConnectivityManager

class RetrofitNetworkClient(
    private val headHunterApi: HeadHunterApi,
    private val connectivityManager: ConnectivityManager
)
