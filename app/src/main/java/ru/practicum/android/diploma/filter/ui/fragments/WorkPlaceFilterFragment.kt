package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceFilterBinding
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.models.FilterUiState
import ru.practicum.android.diploma.filter.ui.view_models.WorkPlaceViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject


class WorkPlaceFilterFragment : Fragment(R.layout.fragment_work_place_filter) {

    private val binding by viewBinding<FragmentWorkPlaceFilterBinding>()
    private val viewModel: WorkPlaceViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    @Inject lateinit var debouncer: Debouncer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        viewModel.getUserDataFromPrefs()

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.uiState.collect { state ->
                viewModel.log("CountryFilterFragment", "state ${state.thisName}")
                when (state) {
                    is FilterUiState.Default  -> {}
                    is FilterUiState.Country  -> renderCountry()
                    is FilterUiState.Region   -> renderRegion()
                    is FilterUiState.FullData -> renderFull()
                    is FilterUiState.Empty   -> {}
                }
            }
        }
    }

    private fun renderFull() {
        viewModel.log(thisName, "renderFull()")
        renderCountry()
        renderRegion()
        binding.chooseBtn.visibility = View.VISIBLE
    }

    private fun renderRegion() {
        viewModel.log(thisName, "renderRegion(${viewModel.region}: String)")
        with(binding) {
            countyHint.visibility = View.VISIBLE
            countryText.text = viewModel.region
            countryItem.setImageResource(R.drawable.close_btn)
            countryText.setTextColor(requireActivity().getColor(R.color.black))
        }
    }

    private fun renderCountry() {
        viewModel.log(thisName, "renderCountry(${viewModel.country}: String)")
        with(binding) {
            countyHint.visibility = View.VISIBLE
            countryText.text = viewModel.country
            countryItem.setImageResource(R.drawable.close_btn)
            countryText.setTextColor(requireActivity().getColor(R.color.black))
        }
    }


    private fun initListeners() {
        with(binding) {
            filterToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            countryContainer.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToCountryFilterFragment()
                )
            }
            regionContainer.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToRegionFilterFragment()
                )
            }
            chooseBtn.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToFilterBaseFragment(
                        viewModel.country,
                        viewModel.region
                    )
                )
            }
        }
    }
}