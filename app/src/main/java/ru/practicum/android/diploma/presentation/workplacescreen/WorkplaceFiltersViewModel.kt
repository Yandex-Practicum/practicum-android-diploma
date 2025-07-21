package ru.practicum.android.diploma.presentation.workplacescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterParameters

class WorkplaceFiltersViewModel(
    private val interactor: FiltersParametersInteractor
) : ViewModel() {

    private val _tempCountry = MutableLiveData<TempLocation?>()
    val getTempCountry: LiveData<TempLocation?> = _tempCountry

    private val _tempRegion = MutableLiveData<String?>()
    val getTempRegion: LiveData<String?> = _tempRegion

    private val _paramsSelectedState = MutableLiveData<FilterParameters>()
    val getSelectedParams: LiveData<FilterParameters> = _paramsSelectedState

    private var tempSelectedRegion: String? = null

    init {
        loadParameters()
    }

    fun setTempCountrySelection(id: String?, name: String?) {
        _tempCountry.postValue(TempLocation(id, name))
    }

    fun setTempRegionSelection(regionName: String?, countryName: String?) {
        tempSelectedRegion = regionName
        _tempRegion.postValue(regionName)
        if (countryName != null) {
            setTempCountrySelection(_tempCountry.value?.id, countryName)
        }
    }

    fun saveSelection() {
        _tempCountry.value?.let {
            interactor.selectCountry(it.name, it.id)
        }
        _tempRegion.value?.let {
            interactor.selectRegion(it)
        }
    }

fun loadParameters() {
    val params = interactor.getFiltersParameters()
    _paramsSelectedState.value = params

    if (_tempCountry.value == null) {
        _tempCountry.value = TempLocation(params.countryId, params.countryName)
    }
    if (_tempRegion.value == null) {
        _tempRegion.value = params.regionName
    }
}

    fun clearCountry() {
        interactor.selectCountry(null, null)
        interactor.selectRegion(null, null)
        _tempCountry.postValue(null)
        _tempRegion.postValue(null)
        loadParameters()
    }

    fun clearRegion() {
        interactor.selectRegion(null, null)
        _tempRegion.postValue(null)
        loadParameters()
    }
}
