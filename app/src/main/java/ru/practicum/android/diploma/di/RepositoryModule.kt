package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.CountriesConverter
import ru.practicum.android.diploma.data.dto.country.impl.CountriesRepositoryImpl
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.data.favorites.FavoriteVacanciesRepository
import ru.practicum.android.diploma.data.favorites.impl.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.dto.industries.IndustriesRepository
import ru.practicum.android.diploma.data.dto.industries.impl.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.data.search.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.data.vacancy.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.country.CountriesRepository

val repositoryModule = module {

    factory { CountriesConverter() }

    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

    single<VacancyConverter> {
        VacancyConverter()
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }

    single<CountriesRepository> {
        CountriesRepositoryImpl(get(), get())
    }
    
    factory<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(get(), get())
    }

}
