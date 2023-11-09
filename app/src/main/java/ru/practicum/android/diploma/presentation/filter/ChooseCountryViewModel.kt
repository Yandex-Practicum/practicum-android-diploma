package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.CountriesState
import ru.practicum.android.diploma.util.DataResponse
import ru.practicum.android.diploma.util.NetworkError


class ChooseCountryViewModel(private val countryUseCase: GetCountriesUseCase) : ViewModel() {

    private val countriesStateLiveData = MutableLiveData<CountriesState>()
    fun observeCountriesState(): LiveData<CountriesState> = countriesStateLiveData

    init {
        initScreen()
    }

    private fun initScreen() {
        viewModelScope.launch {
            countryUseCase.invoke().collect { result ->
                processResult(result)
            }
        }
    }

    //todo Добавить strings из ресурсов
    private fun processResult(result: DataResponse<Country>) {
        if (result.data != null) {
            val countries: ArrayList<Country> = arrayListOf()
            countries.addAll(result.data)
            countriesStateLiveData.value =
                CountriesState.DisplayCountries(countries)
        }
        else {
            when (result.networkError!!) {
                NetworkError.BAD_CONNECTION.toString() -> countriesStateLiveData.value =
                    CountriesState.Error("Проверьте подключение к интернету")
                NetworkError.SERVER_ERROR.toString() -> countriesStateLiveData.value =
                    CountriesState.Error("Ошибка сервера")
            }
        }
    }

    fun onCountryClicked(country: Country) {
        //todo
    }

}