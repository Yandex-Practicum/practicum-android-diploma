package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.SomeRepository

class SomeViewModel(
    private val repository: SomeRepository
) : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    init {
        fillData()
    }

    fun fillData() {
        viewModelScope.launch {
            val dataFromRepo = repository.getBusinessLogicData()
            _data.value = dataFromRepo
        }
    }
}
