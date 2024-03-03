package ru.practicum.android.diploma.application

import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.viewModelModule
import ru.practicum.android.diploma.filter.industry.di.industryDomainModule
import ru.practicum.android.diploma.filter.industry.di.industryRepositoryModule

object DiModuleProvider {
    val industryModules = listOf(
        industryRepositoryModule,
        industryDomainModule
    )

    val coreModules = listOf(
        dataModule,
        repositoryModule,
        interactorModule,
        viewModelModule
    )
}
