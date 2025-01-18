package ru.practicum.android.diploma.search.data.repository

import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.common.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
    private val appDatabase: AppDatabase,
) : SearchRepository
