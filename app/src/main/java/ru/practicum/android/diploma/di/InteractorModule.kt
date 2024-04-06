package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.country.CountryInteractor
import ru.practicum.android.diploma.domain.country.impl.CountryInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.region.RegionInteractor
import ru.practicum.android.diploma.domain.region.impl.RegionInteractorImpl
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.IndustriesInteractorImpl

val interactorModule = module {

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }

    single<CountryInteractor> {
        CountryInteractorImpl(get())
    }

    single<RegionInteractor> {
        RegionInteractorImpl(get())
    }

    factory<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }
}
