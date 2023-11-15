package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.filter.Filters

class FilterViewModel(private val interactor: FilterInteractor) : ViewModel() {



    private val changesLiveData = MutableLiveData(false)
    fun observeChanges(): LiveData<Boolean> = changesLiveData

    private val filtersLiveData = MutableLiveData<Filters>()
    fun observeFilters(): LiveData<Filters> = filtersLiveData

    init {
       // getFilters()
    }
    fun getFilters() {
        viewModelScope.launch {
            val filters = interactor.getFilters()
            if (filtersLiveData.value != null) changesLiveData.postValue(true)
            renderFilters(filters)
        }
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

    fun clearFilters() {
        interactor.clearFilters()
        getFilters()
    }
    fun setSalaryStatus(isChecked:Boolean) {
        interactor.setSalaryStatus(isChecked)
    }
}
