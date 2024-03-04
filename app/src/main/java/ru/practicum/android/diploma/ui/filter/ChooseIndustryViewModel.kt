package ru.practicum.android.diploma.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.Constant.SUCCESS_RESULT_CODE
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry

class ChooseIndustryViewModel(private val repository: IndustriesRepository): ViewModel() {
    private val _industriesState = MutableLiveData<IndustriesState>()
    val industriesState: LiveData<IndustriesState> = _industriesState
    var currentList = emptyList<Industry>()
    fun getIndustries() {
        viewModelScope.launch {
            _industriesState.value = IndustriesState.Loading
            val response = repository.get()
            if(response.resultCode == SUCCESS_RESULT_CODE) {
                _industriesState.value = IndustriesState.Success(response.list)
                currentList = response.list
            } else {
                _industriesState.value = IndustriesState.Error
            }
        }
    }
    fun filterIndustries(editText: String) {
        val filteredList = currentList.filter {
            it.name.contains(editText, ignoreCase = true)
        }
        _industriesState.value = IndustriesState.Success(filteredList)
    }
}
