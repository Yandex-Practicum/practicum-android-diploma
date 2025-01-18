package ru.practicum.android.diploma.vacancy.data.repository

import ru.practicum.android.diploma.common.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val networkClient: RetrofitNetworkClient
) : VacancyRepository
