package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.filter.country.impl.CountryRepositoryImpl
import ru.practicum.android.diploma.data.filter.region.impl.RegionRepositoryImpl
import ru.practicum.android.diploma.data.repositories.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.repositories.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.vacancies.VacanciesSearchRepositoryImpl
import ru.practicum.android.diploma.data.vacancies.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.country.CountryRepository
import ru.practicum.android.diploma.domain.country.CountryRepositoryFlow
import ru.practicum.android.diploma.domain.country.CountryRepositoryFlowImpl
import ru.practicum.android.diploma.domain.favorite.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.filter.FiltersRepository
import ru.practicum.android.diploma.domain.filter.impl.FiltersRepositoryImpl
import ru.practicum.android.diploma.domain.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.region.RegionRepository

val repositoryModule = module {
    factory<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

    single<VacanciesSearchRepository> {
        VacanciesSearchRepositoryImpl(get())
    }

    single<CountryRepository> {
        CountryRepositoryImpl(get(), get())
    }

    single<RegionRepository> {
        RegionRepositoryImpl(get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }

    single<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(get())
    }

    single<FiltersRepository> {
        FiltersRepositoryImpl(get(), get(), get(), get(), get())
    }

    single<CountryRepositoryFlow> {
        CountryRepositoryFlowImpl(get())
    }

    single { listOf("Россия", "Украина", "Казахстан", "Азербайджан", "Беларусь", "Грузия", "Кыргызстан", "Узбекистан") }
}
