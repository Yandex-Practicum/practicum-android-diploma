package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryIndustriesFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryBooleanFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryTextFlow
import ru.practicum.android.diploma.domain.filter.FilterUpdateFlowRepository
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositoryCountryFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositoryIndustriesFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositoryRegionFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositorySalaryBooleanFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositorySalaryTextFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterUpdateFlowRepositoryImpl

val filterModule = module {

    single<FilterRepositoryCountryFlow> {
        FilterRepositoryCountryFlowImpl(get())
    }

    single<FilterRepositoryRegionFlow> {
        FilterRepositoryRegionFlowImpl(get())
    }

    single<FilterRepositoryIndustriesFlow> {
        FilterRepositoryIndustriesFlowImpl(get())
    }

    single<FilterRepositorySalaryTextFlow> {
        FilterRepositorySalaryTextFlowImpl(get())
    }

    single<FilterRepositorySalaryBooleanFlow> {
        FilterRepositorySalaryBooleanFlowImpl(get())
    }

    single<FilterUpdateFlowRepository> {
        FilterUpdateFlowRepositoryImpl(get())
    }
}
