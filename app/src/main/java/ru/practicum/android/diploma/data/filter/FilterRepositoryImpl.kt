package ru.practicum.android.diploma.data.filter

import ru.practicum.android.diploma.data.filter.local.LocalStorage
import ru.practicum.android.diploma.domain.models.filter.FilterRepository

class FilterRepositoryImpl(
    val locale: LocalStorage,
) : FilterRepository {
    override fun setSalary(input: String) {
        locale.setSalary(input)
    }

    override fun getSalary(): String {
        return locale.getSalary()
    }


    /*  override fun getArea(id: String): Flow<Resource<List<Area>>> = flow {
          val response = networkClient.doRequest(AreaRequest(id))
          when (response.resultCode) {
              ERROR -> {
                  emit(Resource.Error(resourceProvider.getString(R.string.check_connection)))
              }

              SUCCESS -> {
                  with(response as RegionListDto) {
                      val countryList = counries.map { mapper.mapCoyntryFromDto(it) }
                      emit(Resource.Success(countryList))
                  }

              }
              else -> {
                  emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
              }
          }
      } */



}