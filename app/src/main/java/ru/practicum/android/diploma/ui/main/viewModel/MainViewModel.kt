package ru.practicum.android.diploma.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.ui.main.SearchState

class MainViewModel : ViewModel() {
    private val state = MutableLiveData<SearchState>()
    fun getSearchState(): LiveData<SearchState> = state
}
