package ru.practicum.android.diploma.filter.profession.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.profession.domain.model.Industry
import ru.practicum.android.diploma.filter.profession.domain.usecase.ProfessionInteractor

class ProfessionViewModel(
    private val application: Application, private val professionInteractor: ProfessionInteractor
) : AndroidViewModel(application) {

    private var _industriesListLiveData = MutableLiveData<List<Industry>?>()
    val industriesListLiveData: LiveData<List<Industry>?> = _industriesListLiveData
    var unfilteredList: List<Industry>? = emptyList<Industry>()

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
                if (response.first == null){
                    Log.d(TAG,"null")//todo delete
                    unfilteredList = null
                _industriesListLiveData.postValue(unfilteredList)}
                else{
                    Log.d(TAG,"not null")//todo delete
                    unfilteredList = response.first
                    _industriesListLiveData.postValue(unfilteredList)
                }
            }
        }

    }

    fun filterList(query: String) {
        val newList = _industriesListLiveData.value
        _industriesListLiveData.value = newList?.filter { it.name.contains(query) }
    }

    fun resetToFullList() {
        _industriesListLiveData.value = unfilteredList
    }
}
