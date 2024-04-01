package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchViewState>()

    fun observeState(): LiveData<SearchViewState> = stateLiveData
}
