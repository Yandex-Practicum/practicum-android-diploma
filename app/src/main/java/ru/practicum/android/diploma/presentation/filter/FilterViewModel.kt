package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.filter.Filters

class FilterViewModel(private val interactor: FilterInteractor) : ViewModel() {

    init {
    //      getFilters()
    }

    private val changesLiveData = MutableLiveData(false)
    fun observeChanges(): LiveData<Boolean> = changesLiveData

    private val filtersLiveData = MutableLiveData<Filters>()
    fun observeFilters(): LiveData<Filters> = filtersLiveData


    fun getFilters() {
        val filters = interactor.getFilters()
        if (filtersLiveData.value != null) changesLiveData.postValue(true)
        renderFilters(filters)
    }

    fun checkChanges(inputText: String){
        if (inputText != (filtersLiveData.value?.preferSalary ?: "")) changesLiveData.postValue(true)
    }


    private fun renderFilters(filters: Filters) {
        filtersLiveData.postValue(filters)
    }


    fun setSalary(inputText: String) {
        interactor.setSalary(inputText)
    }
}
