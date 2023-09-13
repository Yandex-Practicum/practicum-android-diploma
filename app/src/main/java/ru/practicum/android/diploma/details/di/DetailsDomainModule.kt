package ru.practicum.android.diploma.details.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.details.domain.api.AddVacancyToFavoritesUseCase
import ru.practicum.android.diploma.details.domain.api.CheckIfVacancyInFavsUseCase
import ru.practicum.android.diploma.details.domain.api.GetFullVacancyInfoByIdUseCase
import ru.practicum.android.diploma.details.domain.api.RemoveVacancyFromFavoritesUseCase
import ru.practicum.android.diploma.details.domain.impl.AddVacancyToFavoritesUseCaseImpl
import ru.practicum.android.diploma.details.domain.impl.CheckIfVacancyInFavsUseCaseImpl
import ru.practicum.android.diploma.details.domain.impl.GetFullVacancyInfoByIdUseCaseImpl
import ru.practicum.android.diploma.details.domain.impl.RemoveVacancyFromFavoritesUseCaseImpl

@Module
interface DetailsDomainModule {
    @Binds
    fun bindGetFullVacancyInfoByIdUseCase(getFullVacancyInfoByIdUseCaseImpl: GetFullVacancyInfoByIdUseCaseImpl): GetFullVacancyInfoByIdUseCase

    @Binds
    fun bindAddVacancyToFavoritesUseCase(addVacancyToFavoritesUseCaseImpl: AddVacancyToFavoritesUseCaseImpl): AddVacancyToFavoritesUseCase

    @Binds
    fun bindRemoveVacancyFromFavoritesUseCase(removeVacancyFromFavoritesUseCaseImpl: RemoveVacancyFromFavoritesUseCaseImpl): RemoveVacancyFromFavoritesUseCase

    @Binds
    fun bindCheckifInFavsUseCase(checkIfInFavsUseCase: CheckIfVacancyInFavsUseCaseImpl): CheckIfVacancyInFavsUseCase
}