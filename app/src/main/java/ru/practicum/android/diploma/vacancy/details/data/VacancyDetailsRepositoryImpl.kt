package ru.practicum.android.diploma.vacancy.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.search.data.mapper.DtoMapper
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.Resource
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import java.sql.SQLException

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val dtoMapper: DtoMapper,
    private val favoritesInteractor: FavoritesVacanciesInteractor
) : VacancyDetailsRepository {

    private val favoriteToVacancyDetailMapper = FavoriteToVacancyDetailMapper()

    override fun getDetailsFromApi(id: String): Flow<Result<VacancyDetail>> = flow {
        val resource = networkClient.getVacancyById(id)
        when (resource) {
            is Resource.Success -> {
                val vacancyDetail = dtoMapper.vacancyDetailDtoToDomain(resource.data)
                emit(Result.Success(vacancyDetail))
            }
            is Resource.Error -> {
                val throwable = resource.exception ?: Exception(resource.message)
                emit(Result.Error(throwable))
            }
        }
    }

    override fun getDetailsFromDataBase(id: String): Flow<Result<VacancyDetail>> = flow {
        try {
            val favoriteEntity = favoritesInteractor.getFavoriteById(id)
            if (favoriteEntity != null) {
                val vacancyDetail = with(favoriteToVacancyDetailMapper) {
                    favoriteEntity.toVacancyDetail()
                }
                emit(Result.Success(vacancyDetail))
            } else {
                emit(Result.Error(NoSuchElementException("Вакансия не найдена в избранном")))
            }
        } catch (e: IllegalStateException) {
            emit(Result.Error(e))
        } catch (e: SQLException) {
            emit(Result.Error(e))
        }
    }

//    override fun getDetailsFromDataBase(id: String): Flow<Result<VacancyDetail>> = flow {
//        try {
//            val favoriteEntity = favoritesInteractor.getFavoriteById(id)
//            if (favoriteEntity != null) {
//                val vacancyDetail = with(favoriteToVacancyDetailMapper) {
//                    favoriteEntity.toVacancyDetail()
//                }
//                emit(Result.Success(vacancyDetail))
//            } else {
//                emit(Result.Error(Exception("Вакансия не найдена в избранном")))
//            }
//        } catch (e: Exception) {
//            emit(Result.Error(e))
//        }
//    }
}
