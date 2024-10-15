package ru.practicum.android.diploma.filter.place.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentPlaceBinding
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState

internal class PlaceFragment : Fragment() {

    private val regionsCountriesViewModel: RegionsCountriesViewModel by viewModel()

    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!

    private var placeInstance: Place = Place(
        idCountry = null,
        nameCountry = null,
        idRegion = null,
        nameRegion = null
    )

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

        regionsCountriesViewModel.initDataFromNetworkAndSp()

        regionsCountriesViewModel.observePlaceState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.clickCountry.setOnClickListener(listener)
        binding.country.setOnClickListener(listener)
        binding.inputCountry.setOnClickListener(listener)
        binding.inputCountryLayout.setOnClickListener(listener)

        binding.clickRegion.setOnClickListener(listener)
        binding.region.setOnClickListener(listener)
        binding.inputRegion.setOnClickListener(listener)
        binding.inputRegionLayout.setOnClickListener(listener)

        binding.clickCountryClear.setOnClickListener(listener)
        binding.clickRegionClear.setOnClickListener(listener)
        binding.buttonBack.setOnClickListener(listener)
        binding.selectButton.setOnClickListener(listener)
    }

    @SuppressLint("SetTextI18n")
    private fun render(state: PlaceState?) {
        when (state) {
            is PlaceState.ContentCountry -> {
                placeInstance = Place(
                    idCountry = state.country.id,
                    nameCountry = state.country.name,
                    idRegion = null,
                    nameRegion = null
                )
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
        binding.inputRegion.setText("")
        binding.clickCountryClear.visibility = View.VISIBLE
        binding.clickCountry.visibility = View.GONE
        binding.clickRegionClear.visibility = View.GONE
        binding.clickRegion.visibility = View.VISIBLE
        binding.selectButton.visibility = View.VISIBLE
    }

    private fun updateViewsForPlace(place: Place) {
        binding.inputCountry.setText(place.nameCountry)
        binding.inputRegion.setText(place.nameRegion)
        binding.clickCountryClear.visibility = View.VISIBLE
        binding.clickCountry.visibility = View.GONE
        binding.clickRegionClear.visibility = View.VISIBLE
        binding.clickRegion.visibility = View.GONE
        binding.selectButton.visibility = View.VISIBLE
    }

    private fun resetViews() {
        placeInstance = Place(
            idCountry = null,
            nameCountry = null,
            idRegion = null,
            nameRegion = null
        )
        binding.inputCountry.text?.clear()
        binding.inputRegion.text?.clear()
        binding.clickCountryClear.visibility = View.GONE
        binding.clickCountry.visibility = View.VISIBLE
        binding.clickRegionClear.visibility = View.GONE
        binding.clickRegion.visibility = View.VISIBLE
        binding.selectButton.visibility = View.GONE
    }

    private val listener: View.OnClickListener = View.OnClickListener { view ->
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
                regionsCountriesViewModel.mergeBufferWithSettingsDataInSp()
                regionsCountriesViewModel.clearCache()
                findNavController().navigate(
                    R.id.action_placeFragment_to_filterFragment,
                    createArgs(placeInstance)
                )
            }
            R.id.buttonBack -> {
                regionsCountriesViewModel.mergeSettingsWithBufferDataInSp()
                regionsCountriesViewModel.clearCache()
                findNavController().navigateUp()
            }
        }
    }

    private fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }

    private fun clearCountrySelection() {
        placeInstance = Place(
            null,
            null,
            null,
            null
        )
        regionsCountriesViewModel.setPlaceState(PlaceState.Empty)
        regionsCountriesViewModel.setPlaceInDataFilter(placeInstance)
    }

    private fun clearRegionSelection() {
        placeInstance = placeInstance.copy(idRegion = null, nameRegion = null)
        regionsCountriesViewModel.setPlaceState(
            PlaceState.ContentCountry(
                Country(
                    id = placeInstance.idCountry ?: "",
                    name = placeInstance.nameCountry ?: ""
                )
            )
        )
        regionsCountriesViewModel.setPlaceInDataFilter(placeInstance.copy(idRegion = null, nameRegion = null))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARGS_PLACE_COUNTRY_ID = "id_country"
        private const val ARGS_PLACE_COUNTRY_NAME = "name_country"
        private const val ARGS_PLACE_REGION_ID = "id_region"
        private const val ARGS_PLACE_REGION_NAME = "name_region"
        fun createArgs(selectedPlace: Place): Bundle =
            bundleOf(
                ARGS_PLACE_COUNTRY_ID to selectedPlace.idCountry,
                ARGS_PLACE_COUNTRY_NAME to selectedPlace.nameCountry,
                ARGS_PLACE_REGION_ID to selectedPlace.idRegion,
                ARGS_PLACE_REGION_NAME to selectedPlace.nameRegion
            )
    }
}
