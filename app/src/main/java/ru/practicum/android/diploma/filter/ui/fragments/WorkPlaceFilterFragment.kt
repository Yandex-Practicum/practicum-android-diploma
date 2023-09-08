package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceFilterBinding
import ru.practicum.android.diploma.filter.ui.models.SelectedFilter
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
        viewModel.checkSavedFilterData()
        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state -> render(state)
                viewModel.log("CountryFilterFragment", "uiState.collect { $state")
            }
        }
    }

    private fun render(data: SelectedFilter) {
        viewModel.log(thisName,"render($data: SelectedFilter)")
        with(binding) {
            countryText.setText(data.country?.name ?: "")
            regionText.setText(data.region?.name ?: "")
            showButton(countryText.text)
        }
    }

    private fun showButton(country: Editable?) {
        if (country.isNullOrEmpty()) binding.chooseBtn.visibility = View.GONE
        else binding.chooseBtn.visibility = View.VISIBLE
    }

    private fun initListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            country.debounceClickListener(debouncer) {
                viewModel.saveRegion(null)
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFragmentToCountryFragment()
                )
            }
            region.debounceClickListener(debouncer) {
                viewModel.saveRegion(null)
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFragmentToRegionFragment()
                )
            }
            chooseBtn.debounceClickListener(debouncer) {
                findNavController().navigateUp()
            }
        }
    }
}