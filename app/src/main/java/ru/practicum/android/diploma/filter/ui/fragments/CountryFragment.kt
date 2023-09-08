package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.ui.fragments.painters.CountryPainter
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.thisName

open class CountryFragment : ChooseFragment() {

    override val fragment = COUNTRY
    override val viewModel: CountryViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val painter = CountryPainter(binding)

        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "state ${state.thisName}")
                when (state) {
                    is UiState.Loading -> painter.showLoadingScreen()
                    is UiState.Content<*> -> renderContent(state.list)
                    is UiState.NoData -> painter.showNoData(state.message)
                    is UiState.Offline -> painter.showOffline(state.message)
                    is UiState.Error -> painter.showError(state.message)
                }
            }
        }
    }

    override fun initListeners() {
        super.initListeners()
        filterAdapter.onClickCountry = { country ->
            viewModel.saveCountry(country)
            viewModel.saveRegion(null)
            findNavController().navigateUp()
        }
    }

    companion object { const val COUNTRY = "CountryFragment" }
}