package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.utils.NumericConstants
import ru.practicum.android.diploma.utils.debounce

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<SearchState>(SearchState.Content(emptyList()))
    val screenState: LiveData<SearchState> = _screenState

    private var latestSearchText: String? = null

    private val options: HashMap<String, String> = HashMap()

    private val searchDebounce = debounce<String>(
        delayMillis = NumericConstants.TWO_SECONDS,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        searchRequest(changedText)
    }

    fun search(searchText: String) {
        if (searchText.isBlank() || latestSearchText == searchText) {
            return
        }
        searchDebounce(searchText)
    }

    private fun searchRequest(searchText: String) {

        if (searchText.isNotEmpty()) {
            options["text"] = searchText
        }

        _screenState.value = SearchState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            searchInteractor.search(SearchRequest(options)).collect { response ->
                if (response.resultCode == 200) {
                    _screenState.postValue(SearchState.Content(response.results))
                } else {
                    _screenState.postValue(SearchState.Error)
                }
            }
        }
    }
}
