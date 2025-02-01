package ru.practicum.android.diploma.ui.filter.countryregion.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCountryRegionBinding
import ru.practicum.android.diploma.ui.filter.countryregion.viewmodel.FilterCountryRegionViewModel
import ru.practicum.android.diploma.util.FilterNames

class FilterCountryRegionFragment : Fragment() {

    private lateinit var binding: FragmentFilterCountryRegionBinding
    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("location_prefs", Context.MODE_PRIVATE)
    }

    private val viewModel: FilterCountryRegionViewModel by lazy {
        FilterCountryRegionViewModel(sharedPreferences)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentFilterCountryRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton()
        setupListeners()
        selectButton()

        arguments?.let {
            val countryId = it.getString(FilterNames.COUNTRY_ID)
            val countryName = it.getString(FilterNames.COUNTRY_NAME)
            val regionId = it.getString(FilterNames.REGION_ID)
            val regionName = it.getString(FilterNames.REGION_NAME)

            viewModel.setCountry(countryId, countryName)
            viewModel.setRegion(regionId, regionName)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.countryName.combine(viewModel.regionName) { countryName, regionName ->
                    countryName to regionName
                }.collect { (countryName, regionName) ->
                    renderCountry(countryName)
                    renderRegion(regionName)
                }
            }
        }
    }

    private fun backButton() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListeners() {
        setupFieldListeners(
            view = binding.country,
            onClearField = { viewModel.clearCountry() },
            onClickEmptyField = {
                findNavController().navigate(R.id.action_filterCountryRegionFragment_to_filterCountryFragment)
            },
        )
        setupFieldListeners(
            view = binding.region,
            onClearField = { viewModel.clearRegion() },
            onClickEmptyField = {
                val bundle = Bundle().apply {
                    putString(FilterNames.COUNTRY_ID, viewModel.countryId.value)
                }
                findNavController().navigate(R.id.action_filterCountryRegionFragment_to_filterRegionFragment, bundle)
            },
        )
    }

    private fun setupFieldListeners(
        view: TextInputLayout,
        onClearField: () -> Unit,
        onClickEmptyField: () -> Unit,
    ) {
        view.editText?.setOnClickListener {
            onClickEmptyField()
        }

        view.setEndIconOnClickListener {
            if (view.editText?.text.isNullOrEmpty()) {
                onClickEmptyField()
            } else {
                onClearField()
                renderField(view = view, text = null)
            }
        }
    }

    private fun renderField(view: TextInputLayout, text: String?) {
        view.editText?.setText(text)

        val typedArray = context?.theme?.obtainStyledAttributes(
            intArrayOf(R.attr.blackToWhite)
        )
        val colorStateList = typedArray?.getColorStateList(0)
        typedArray?.recycle()

        when {
            view.editText?.text.isNullOrEmpty() -> {
                view.setEndIconDrawable(R.drawable.ic_arrow_forward_48)
                view.defaultHintTextColor = requireContext().getColorStateList(R.color.gray)
            }

            else -> {
                view.setEndIconDrawable(R.drawable.ic_close_24)
                view.defaultHintTextColor = colorStateList
            }
        }
    }

    private fun renderCountry(countryName: String?) {
        binding.country.editText?.setText(countryName)
    }

    private fun renderRegion(regionName: String?) {
        binding.region.editText?.setText(regionName)
    }

    private fun selectButton() {
        binding.buttonSelect.setOnClickListener {
            viewModel.saveFilter()
            val bundle = Bundle().apply {
                putString(FilterNames.COUNTRY_ID, viewModel.countryId.value)
                putString(FilterNames.COUNTRY_NAME, viewModel.countryName.value)
                putString(FilterNames.REGION_ID, viewModel.regionId.value)
                putString(FilterNames.REGION_NAME, viewModel.regionName.value)
            }
            findNavController().navigate(R.id.action_filterCountryRegionFragment_to_filterCommonFragment, bundle)
        }
    }
}
