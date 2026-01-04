package ru.practicum.android.diploma.vacancy.details.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.data.FavoriteToVacancyDetailMapper
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import ru.practicum.android.diploma.vacancy.details.domain.model.VacancyDetailsSource
import ru.practicum.android.diploma.search.domain.model.Result as SearchResult

private const val HTTP_NOT_FOUND = 404

class VacancyDetailsInteractorImpl(
    private val searchInteractor: SearchInteractor,
    private val favoritesInteractor: FavoritesVacanciesInteractor,
    private val favoriteToVacancyDetailMapper: FavoriteToVacancyDetailMapper,
) : VacancyDetailsInteractor {

    override fun getVacancyById(
        id: String,
        source: VacancyDetailsSource,
    ): Flow<Result<VacancyDetail>> {
        return flow {
            searchInteractor.getVacancyById(id).collect { searchResult ->
                when (searchResult) {
                    is SearchResult.Success -> {
                        emit(Result.Success(searchResult.data))
                    }

                    is SearchResult.Error -> {
                        handleError(id, source, searchResult) { result ->
                            emit(result)
                        }
                    }
                }
            }
        }
    }

    private suspend fun handleError(
        id: String,
        source: VacancyDetailsSource,
        searchResult: SearchResult.Error,
        emitResult: suspend (Result<VacancyDetail>) -> Unit,
    ) {
        val throwable = searchResult.exception?.let { exception ->
            Exception(searchResult.message, exception)
        } ?: Exception(searchResult.message)

        // если переход из избранного и нет интернета, загружаем из БД
        if (source == VacancyDetailsSource.FAVORITES &&
            searchResult.message == "Нет подключения к интернету"
        ) {
            val favoriteEntity = favoritesInteractor.getFavoriteById(id)
            if (favoriteEntity != null) {
                val vacancyDetail = with(favoriteToVacancyDetailMapper) {
                    favoriteEntity.toVacancyDetail()
                }
                emitResult(Result.Success(vacancyDetail))
                return
            }
        }

        // если переход из избранного и вакансия не найдена (404), удаляем из БД
        if (source == VacancyDetailsSource.FAVORITES &&
            isNotFoundError(throwable)
        ) {
            favoritesInteractor.removeFromFavorites(id)
        }

        emitResult(Result.Error(throwable))
    }
}

private fun isNotFoundError(throwable: Throwable?): Boolean {
    val httpCode = when (throwable) {
        null -> null
        is HttpException -> throwable.code()
        else -> (throwable.cause as? HttpException)?.code()
    }

    return when {
        httpCode != null -> httpCode == HTTP_NOT_FOUND
        else -> throwable?.message == "404 Not Found"
    }
}
