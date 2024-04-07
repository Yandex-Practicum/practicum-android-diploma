package ru.practicum.android.diploma.ui.filter.industries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndustriesViewModel() : ViewModel() {
    private val stateLiveData = MutableLiveData<IndustriesState>()
    fun observeState(): LiveData<IndustriesState> = stateLiveData
}
