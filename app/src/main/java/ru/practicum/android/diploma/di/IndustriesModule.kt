package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.data.dto.favourites.IndustriesRepositoryImpl
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.ui.filter.ChooseIndustryViewModel

val industriesModule = module {
    factory {
        IndustriesRepositoryImpl(get())
    } bind IndustriesRepository::class
    viewModelOf(::ChooseIndustryViewModel)
}
