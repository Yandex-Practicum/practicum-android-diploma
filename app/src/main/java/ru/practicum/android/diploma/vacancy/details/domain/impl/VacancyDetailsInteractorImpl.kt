package ru.practicum.android.diploma.vacancy.details.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import ru.practicum.android.diploma.vacancy.details.domain.model.VacancyDetailsSource
import ru.practicum.android.diploma.search.domain.model.Result as SearchResult

class VacancyDetailsInteractorImpl(
    private val searchInteractor: SearchInteractor,
    private val favoritesInteractor: FavoritesVacanciesInteractor,
//    private val dtoMapper: DtoMapper
) : VacancyDetailsInteractor {

    override fun getVacancyById(
        id: String,
        source: VacancyDetailsSource
    ): Flow<Result<VacancyDetail>> {
        return flow {
            searchInteractor.getVacancyById(id)
                .collect { searchResult ->
                    when (searchResult) {
                        is SearchResult.Success -> {
                            emit(Result.Success(searchResult.data))
                        }
                        is SearchResult.Error -> {
                            val throwable = searchResult.exception ?: Exception(searchResult.message)

                            // Бизнес-логика: если переход из избранного и вакансия не найдена (404), удаляем из БД
                            if (source == VacancyDetailsSource.FAVORITES &&
                                isNotFoundError(throwable)
                            ) {
                                favoritesInteractor.removeFromFavorites(id)
                            }

                            emit(Result.Error(throwable))
                        }
                    }
                }
        }
    }

    private fun isNotFoundError(throwable: Throwable?): Boolean {
        if (throwable == null) return false
        if (throwable.message == "404 Not Found") return true

        val httpException = when (throwable) {
            is HttpException -> throwable
            else -> throwable.cause as? HttpException
        }

        return httpException?.code() == 404
    }
}
