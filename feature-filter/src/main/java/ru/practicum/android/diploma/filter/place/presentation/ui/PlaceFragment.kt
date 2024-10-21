package ru.practicum.android.diploma.filter.place.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentPlaceBinding
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.model.resetRegion
import ru.practicum.android.diploma.filter.place.domain.model.setCountry
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.PlaceViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState

internal class PlaceFragment : Fragment() {

    private val placeViewModel: PlaceViewModel by viewModel()

    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!

    private var placeInstance: Place = Place.emptyPlace()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeViewModel.initDataFromSp()

        placeViewModel.observePlaceState().observe(viewLifecycleOwner) {
            render(it)
        }

        placeViewModel.observePlaceButtonSelectedState().observe(viewLifecycleOwner) {
            renderVisibleButtonSelected(it)
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.clickCountry, R.id.country, R.id.inputCountry, R.id.inputCountryLayout -> {
                    navigateTo(R.id.action_placeFragment_to_countryFragment)
                }
                R.id.clickRegion, R.id.region, R.id.inputRegion, R.id.inputRegionLayout -> {
                    navigateTo(R.id.action_placeFragment_to_regionFragment)
                }
                R.id.clickCountryClear -> clearCountrySelection()
                R.id.clickRegionClear -> clearRegionSelection()
                R.id.selectButton -> {
                    placeViewModel.copyReserveBufferInSettingsDataInSpBuffer()
                    placeViewModel.clearCache()
                    findNavController().navigateUp()
                }
                R.id.buttonBack -> {
                    placeViewModel.copyBufferInSettingDataInSpReserveBuffer()
                    placeViewModel.clearCache()
                    findNavController().navigateUp()
                }
            }
        }

        listOf(
            binding.clickCountry,
            binding.country,
            binding.inputCountry,
            binding.inputCountryLayout,
            binding.clickRegion,
            binding.region,
            binding.inputRegion,
            binding.inputRegionLayout,
            binding.clickCountryClear,
            binding.clickRegionClear,
            binding.buttonBack,
            binding.selectButton
        ).forEach { it.setOnClickListener(clickListener) }
    }

    private fun renderVisibleButtonSelected(isVisible: Boolean?) {
        if (isVisible == null || !isVisible) {
            binding.selectButton.visibility = View.GONE
        } else {
            binding.selectButton.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun render(state: PlaceState?) {
        when (state) {
            is PlaceState.ContentCountry -> {
                placeInstance = Place.emptyPlace().setCountry(state.country)
                updateViewsForCountry(state.country)
            }

            is PlaceState.ContentPlace -> {
                placeInstance = state.place
                updateViewsForPlace(state.place)
            }

            else -> {
                resetViews()
            }
        }
    }

    private fun updateViewsForCountry(country: Country) {
        binding.inputCountry.setText(country.name)
        binding.inputRegion.text?.clear()
        binding.clickCountryClear.visibility = View.VISIBLE
        binding.clickCountry.visibility = View.GONE
        binding.clickRegionClear.visibility = View.GONE
        binding.clickRegion.visibility = View.VISIBLE
        placeViewModel.checkTheSelectButton()
    }

    private fun updateViewsForPlace(place: Place) {
        binding.inputCountry.setText(place.nameCountry)
        binding.inputRegion.setText(place.nameRegion)
        binding.clickCountryClear.visibility = View.VISIBLE
        binding.clickCountry.visibility = View.GONE
        binding.clickRegionClear.visibility = View.VISIBLE
        binding.clickRegion.visibility = View.GONE
        placeViewModel.checkTheSelectButton()
    }

    private fun resetViews() {
        placeInstance = Place.emptyPlace()
        binding.inputCountry.text?.clear()
        binding.inputRegion.text?.clear()
        binding.clickCountryClear.visibility = View.GONE
        binding.clickCountry.visibility = View.VISIBLE
        binding.clickRegionClear.visibility = View.GONE
        binding.clickRegion.visibility = View.VISIBLE
        placeViewModel.checkTheSelectButton()
    }

    private fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }

    private fun clearCountrySelection() {
        placeInstance = Place.emptyPlace()
        placeViewModel.setPlaceState(PlaceState.Empty)
        placeViewModel.setPlaceInDataFilterReserveBuffer(placeInstance)
    }

    private fun clearRegionSelection() {
        placeInstance = placeInstance.resetRegion()
        placeViewModel.setPlaceState(
            PlaceState.ContentCountry(
                Country(
                    id = placeInstance.idCountry ?: "",
                    name = placeInstance.nameCountry ?: ""
                )
            )
        )
        placeViewModel.setPlaceInDataFilterReserveBuffer(placeInstance.resetRegion())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
