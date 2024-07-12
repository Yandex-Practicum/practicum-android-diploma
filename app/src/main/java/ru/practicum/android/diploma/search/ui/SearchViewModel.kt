package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_SUCCESS
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.utils.NumericConstants
import ru.practicum.android.diploma.utils.debounce
import java.util.concurrent.atomic.AtomicBoolean

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<SearchState>(SearchState.Empty)
    val screenState: LiveData<SearchState> = _screenState

    private var latestSearchText: String? = null

    private val options = mutableMapOf<String, String>()

    private val searchDebounce = debounce<String>(
        delayMillis = NumericConstants.TWO_SECONDS,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        searchRequest(changedText)
    }

    private var isNextPageLoading = AtomicBoolean(false)

    fun onLastItemReached() {
        if (isNextPageLoading.get()) {
            return
        }
        isNextPageLoading.set(true)


    }

    fun search(searchText: String) {
        if (searchText.isNotEmpty() || latestSearchText != searchText) {
            latestSearchText = searchText
            searchDebounce(searchText)
        } else {
            _screenState.postValue(SearchState.Empty)
        }
    }

    private fun searchRequest(searchText: String) {
        if (searchText.isNotEmpty()) {
            _screenState.postValue(SearchState.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                options["text"] = searchText
                searchInteractor.search(SearchRequest(options)).collect { response ->
                    if (response.resultCode == RESULT_CODE_SUCCESS) {
                        _screenState.postValue(SearchState.Content(response.results, response.foundVacancies))
                    } else {
                        _screenState.postValue(SearchState.Error)
                    }
                }
            }
        }
    }
}
