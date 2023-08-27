package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.data.network.NetworkClient
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val retrofitClient: NetworkClient, logger: Logger): BaseViewModel(logger) {
    init {
        viewModelScope.launch {
            retrofitClient.doRequest("pink")
        }
    }
}