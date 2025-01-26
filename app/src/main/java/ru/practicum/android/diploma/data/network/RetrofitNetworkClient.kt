package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.AreasResponse
import ru.practicum.android.diploma.data.dto.Request
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyResponse

class RetrofitNetworkClient(private val vacancyService: VacancyApi) : NetworkClient {
    companion object {
        private const val RETROFIT_LOG = "RETROFIT_LOG"
        private const val SuccessfulRequest = 200
        private const val BadRequest = 400
    }

    override suspend fun doRequest(dto: Request): Response {
        return withContext(Dispatchers.IO) {
            try {
                val resp = when (dto) {
                    is Request.VacanciesRequest -> {
                        vacancyService.searchVacancy(dto.options)
                    }

                    is Request.VacancyRequest -> {
                        VacancyResponse(vacancyService.getVacancy(dto.id))
                    }

                    is Request.CountriesRequest -> {
                        AreasResponse(vacancyService.getCountries())
                    }

                    is Request.AreasRequest -> {
                        vacancyService.getAreasById(dto.id)
                    }

                    is Request.ProfessionalRolesRequest -> {
                        vacancyService.getProfessionalRoles()
                    }
                }

                resp.apply { resultCode = SuccessfulRequest }
            } catch (e: HttpException) {
                Log.d(
                    RETROFIT_LOG,
                    "${e.message}"
                )
                Response().apply { resultCode = BadRequest }
            }
        }
    }
}
