package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState

class FilterCountriesViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {
    private var selectedCountry: Country? = null
   // private val state = MutableLiveData<CountryViewState>()
   // fun observeState(): LiveData<IndustryViewState> = state {

   // }
}
