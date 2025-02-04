package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.models.Filter

class FilterPlaceOfWorkViewModel(
    private val sharedPrefsInteractor: SharedPrefsInteractor
) : ViewModel() {
    private var filter = MutableLiveData<Filter>()

    init {
        loadData()
    }

    fun updateFilter(filter: Filter) {
        sharedPrefsInteractor.updateFilter(filter)
        loadData()
    }

    private fun getFilter() = sharedPrefsInteractor.getFilter()

    fun clearFilterField(field: String) {
        sharedPrefsInteractor.clearFilterField(field)
        loadData()
    }

    private fun loadData() {
        filter.postValue(getFilter())
    }

    fun getFilterLiveData(): MutableLiveData<Filter> {
        return filter
    }

}
