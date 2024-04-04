package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryIndustriesFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryBooleanFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryTextFlow
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositoryCountryFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositoryIndustriesFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositoryRegionFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositorySalaryBooleanFlowImpl
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositorySalaryTextFlowImpl
import ru.practicum.android.diploma.domain.filter.test.FilterInfoRepository
import ru.practicum.android.diploma.domain.filter.test.FilterInfoRepositoryImpl

val filterModule = module {

    factory<FilterRepositoryCountryFlow> {
        FilterRepositoryCountryFlowImpl(get())
    }

    factory<FilterRepositoryRegionFlow> {
        FilterRepositoryRegionFlowImpl(get())
    }

    factory<FilterRepositoryIndustriesFlow> {
        FilterRepositoryIndustriesFlowImpl(get())
    }

    factory<FilterRepositorySalaryTextFlow> {
        FilterRepositorySalaryTextFlowImpl(get())
    }

    factory<FilterRepositorySalaryBooleanFlow> {
        FilterRepositorySalaryBooleanFlowImpl(get())
    }


}
