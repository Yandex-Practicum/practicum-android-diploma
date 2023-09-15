package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceFilterBinding
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.view_models.WorkPlaceViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class WorkPlaceFilterFragment : Fragment(R.layout.fragment_work_place_filter) {

    @Inject lateinit var debouncer: Debouncer
    private val args by navArgs<WorkPlaceFilterFragmentArgs>()
    private val binding by viewBinding<FragmentWorkPlaceFilterBinding>()
    private val viewModel: WorkPlaceViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleInputArgs(args.selectedFilter)
        initListeners()
        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                render(state)
            }
        }
    }

    private fun render(data: SelectedFilter) {
        viewModel.log(thisName, "render($data: SelectedFilter)")
        with(binding) {
            countryText.setText(data.country?.name ?: "")
            changeIcon(countryText, countryIcon)
            regionText.setText(data.region?.name ?: "")
            changeIcon(regionText, regionIcon)
            showButton(countryText.text)
        }
    }

    private fun showButton(country: Editable?) {
        if (country.isNullOrEmpty()) binding.chooseBtn.visibility = View.GONE
        else binding.chooseBtn.visibility = View.VISIBLE
    }

    private fun initListeners() {
        with(binding) {
            country.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections
                        .actionWorkPlaceFragmentToCountryFragment(viewModel.selectedFilter)
                )
            }
            region.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections
                        .actionWorkPlaceFragmentToRegionFragment(viewModel.selectedFilter)
                )
            }
            chooseBtn.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections
                        .actionWorkPlaceFilterToBaseFilter(viewModel.selectedFilter)
                )
            }
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            countryIcon.debounceClickListener(debouncer) { onCountryIconPush(countryText) }
            regionIcon.debounceClickListener(debouncer) { onRegionIconPush(regionText) }
            
            countryText.doOnTextChanged { text, _, _, _ ->
                renderEditTextColor(countryContainer, text)
            }
            regionText.doOnTextChanged { text, _, _, _ ->
                renderEditTextColor(regionContainer, text)
            }
        }
    }

    private fun onCountryIconPush(view: EditText) {
        if (view.text.isEmpty()) {
            findNavController().navigate(
                WorkPlaceFilterFragmentDirections
                    .actionWorkPlaceFragmentToRegionFragment(viewModel.selectedFilter)
            )
        } else {
            view.setText("")
            viewModel.changeCountry()
            changeIcon(binding.countryText, binding.countryIcon)
        }
    }

    private fun onRegionIconPush(view: EditText) {
        if (view.text.isEmpty()) {
            findNavController().navigate(
                WorkPlaceFilterFragmentDirections
                    .actionWorkPlaceFragmentToRegionFragment(viewModel.selectedFilter)
            )
        } else {
            view.setText("")
            viewModel.changeRegion()
            changeIcon(binding.regionText, binding.regionIcon)
        }
    }
    
    private fun changeIcon(editText: EditText, view: ImageView) {
        if (editText.text.isEmpty()) view.setImageResource(R.drawable.leading_icon)
        else view.setImageResource(R.drawable.ic_clear)
    }
    
    private fun renderEditTextColor(view: TextInputLayout, text: CharSequence?) {
        if (!text.isNullOrEmpty()) {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(), R.color.filter_text_color_enabled
            )
        } else {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(), R.color.filter_text_color
            )
        }
    }
}