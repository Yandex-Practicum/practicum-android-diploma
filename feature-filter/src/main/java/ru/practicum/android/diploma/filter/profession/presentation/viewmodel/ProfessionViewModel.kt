package ru.practicum.android.diploma.filter.profession.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.profession.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.profession.domain.usecase.ProfessionInteractor

class ProfessionViewModel(
    application: Application,
    private val professionInteractor: ProfessionInteractor
) : AndroidViewModel(application) {

    private var _industriesListLiveData = MutableLiveData<List<IndustryModel>?>()
    val industriesListLiveData: LiveData<List<IndustryModel>?> = _industriesListLiveData
    var unfilteredList: List<IndustryModel>? = emptyList<IndustryModel>()

    companion object {
        private const val TAG: String = "ProfessionViewModel"
    }

    init {
        _industriesListLiveData.value = emptyList()
        getIndustriesList()
    }

    private fun getIndustriesList() {
        viewModelScope.launch(Dispatchers.IO) {
            professionInteractor.getIndustriesList().collect { response ->
                if (response.first == null) {
                    unfilteredList = null
                    _industriesListLiveData.postValue(unfilteredList) } else {
                    unfilteredList = response.first
                    _industriesListLiveData.postValue(unfilteredList)
                }
            }
        }

    }

    fun filterList(query: String) {
        val newList = unfilteredList
        _industriesListLiveData.value = newList?.filter { it.name.lowercase().contains(query.lowercase()) }
        // lowercase нужен чтобы не искало с case-sensitive
    }

    fun resetToFullList() {
        _industriesListLiveData.value = unfilteredList
    }
}
