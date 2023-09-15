package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.painters.CountryPainter
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.thisName

open class CountryFragment : ChooseFragment() {

    override val fragment = COUNTRY
    private val args by navArgs<CountryFragmentArgs>()
    override val viewModel: CountryViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectedFilter = args.selectedFilter
        super.onViewCreated(view, savedInstanceState)
        val painter = CountryPainter(binding)

        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is UiState.Loading -> painter.showLoadingScreen()
                    is UiState.Content -> renderContent(state.list)
                    is UiState.NoData -> painter.showNoData(state.message)
                    is UiState.Offline -> painter.showOffline(state.message)
                    is UiState.Error -> painter.showError(state.message)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun renderContent(list: List<Any?>) {
        super.renderContent(list)
        binding.toolbar.title = requireActivity().getString(R.string.choose_country)
        filterAdapter.countryList = list as List<Country>
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
        viewModel.log(thisName, "renderContent: list.size = ${list.size})")
    }

    override fun initListeners() {
        super.initListeners()
        filterAdapter.onClickCountry = { country ->
            viewModel.refreshCountry(country)
            findNavController().navigate(
                CountryFragmentDirections
                    .actionCountryFilterToWorkPlaceFilter(viewModel.selectedFilter)
            )
        }
    }

    companion object { const val COUNTRY = "CountryFragment" }
}