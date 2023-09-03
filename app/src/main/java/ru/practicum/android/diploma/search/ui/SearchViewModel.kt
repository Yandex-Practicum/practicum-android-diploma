package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.data.ResourceProvider
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.SearchState
import ru.practicum.android.diploma.search.domain.models.Vacancy

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private var lastSearchText: String? = null

    private val stateLiveData = MutableLiveData<SearchState>()

    private var _iconStateLiveData = MutableLiveData<IconState>()
    val iconStateLiveData: LiveData<IconState> = _iconStateLiveData

    fun observeState(): LiveData<SearchState> = stateLiveData
    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun clearInputEditText() {
        lastSearchText = null
    }

    fun setOnFocus(editText: String?, hasFocus: Boolean) {
        if (hasFocus && editText.isNullOrEmpty()) _iconStateLiveData.postValue(IconState.SearchIcon)
        if (hasFocus && editText!!.isNotEmpty()) _iconStateLiveData.postValue(IconState.CloseIcon)
        if (!hasFocus && editText!!.isNotEmpty()) _iconStateLiveData.postValue(IconState.SearchIcon)
        if (!hasFocus && editText.isNullOrEmpty()) _iconStateLiveData.postValue(IconState.SearchIcon)
    }

    fun searchVacancy(searchText: String) {
        if (lastSearchText == searchText) {
            return
        } else {
            lastSearchText = searchText
            if (searchText.isNotEmpty()) {
                renderState(SearchState.Loading)

                viewModelScope.launch {
                    interactor.loadVacancies(searchText)
                        .collect { pair ->
                            processResult(pair.first, pair.second)
                        }
                }
            }
        }
    }

    /*  Заготовка для фильтров

    fun getVacancies(newSearchText: String) {
          if (newSearchText.isNotEmpty()) {
              renderState(SearchState.Loading)
              viewModelScope.launch {

                  val options: HashMap<String, String> = HashMap()
                  options["searchRequest"] = searchRequest
                  if (page.isNotEmpty()) options["page"] = page
                  if (perPage.isNotEmpty()) options["per_page"] = perPage //20
                  if (area.isNotEmpty()) options["area"] = area
                  if (industry.isNotEmpty()) options["industry"] = industry
                  if (salary.isNotEmpty()) options["salary"] = salary
                  if (onlyWithSalary.isNotEmpty()) options["only_with_salary"] = onlyWithSalary

                  //   SearchService(options).execute()
                  interactor.getVacancies(options)
                      .collect { pair ->
                          processResult(pair.first, pair.second)
                      }
              }
          }
      }*/

    private fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?) {
        val vacancies = mutableListOf<Vacancy>()
        if (foundVacancies != null) {
            vacancies.addAll(foundVacancies)
        }
        when {
            errorMessage != null -> {
                renderState(
                    SearchState.Error(
                        errorMessage = resourceProvider.getString(R.string.no_connection)
                    )
                )
            }

            vacancies.isEmpty() -> {
                renderState(
                    SearchState.Empty(
                        message = resourceProvider.getString(R.string.no_vacancies)
                    )
                )
            }

            else -> {
                renderState(
                    SearchState.VacancyContent(
                        vacancies = vacancies,
                        foundValue = vacancies[0].found
                    )
                )
            }
        }
    }
}