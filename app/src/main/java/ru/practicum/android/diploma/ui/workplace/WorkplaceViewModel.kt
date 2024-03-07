package ru.practicum.android.diploma.ui.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkplaceViewModel : ViewModel() {

    private val countryLiveData = MutableLiveData<String>()

    fun observeState(): LiveData<String> = countryLiveData
}
