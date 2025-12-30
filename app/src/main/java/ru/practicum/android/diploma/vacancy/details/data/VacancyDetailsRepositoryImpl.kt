package ru.practicum.android.diploma.vacancy.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.details.presentation.viewmodel.VacancyFakeFactory
import ru.practicum.android.diploma.vacancy.details.domain.model.Result

class VacancyDetailsRepositoryImpl : VacancyDetailsRepository {

    override fun getDetailsFromApi(id: String): Flow<Result<VacancyDetail>> = flow {
        emit(Result.Success(VacancyFakeFactory.create()))
    }

    override fun getDetailsFromDataBase(id: String): Flow<Result<VacancyDetail>> = flow {
        emit(Result.Success(VacancyFakeFactory.create()))
    }
}

// возможно нужно удалить репозиторий
//class VacancyDetailsRepositoryImpl : VacancyDetailsRepository {
//    override fun getVacancyById(id: String): Flow<Result<VacancyDetail>> = flow {
//    }
//}
