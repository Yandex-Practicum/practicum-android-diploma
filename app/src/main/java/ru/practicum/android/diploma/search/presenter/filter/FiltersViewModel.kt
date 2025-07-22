package ru.practicum.android.diploma.search.presenter.filter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.search.domain.model.Industry

class FiltersViewModel : ViewModel() {

    private val _selectedIndustry = MutableStateFlow<Industry?>(null)
    val selectedIndustry = _selectedIndustry.asStateFlow()

    fun updateIndustry(industry: Industry?) {
        _selectedIndustry.value = industry
    }
}
