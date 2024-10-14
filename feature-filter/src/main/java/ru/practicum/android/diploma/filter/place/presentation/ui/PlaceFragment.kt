package ru.practicum.android.diploma.filter.place.presentation.ui

import android.os.Bundle
import android.util.Log
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
        Log.e("onViewCreated", "onViewCreated")

        arguments?.let { args ->
            val idCountry = args.getString(ARGS_COUNTRY_ID)
            val nameCountry = args.getString(ARGS_COUNTRY_NAME)
            val idRegion = args.getString(ARGS_REGION_ID)
            val nameRegion = args.getString(ARGS_REGION_NAME)
            when {
                (idCountry != null && nameCountry != null) && (idRegion == null && nameRegion == null) -> {
                    regionsCountriesViewModel.setPlaceState(
                        PlaceState.ContentCountry(
                            country = Country(
                                id = idCountry,
                                name = nameCountry
                            )
                        )
                    )
                }
                (idCountry != null && nameCountry != null) && (idRegion != null && nameRegion != null) -> {
                    regionsCountriesViewModel.setPlaceState(
                        PlaceState.ContentPlace(
                            place = Place(
                                idCountry = idCountry,
                                nameCountry = nameCountry,
                                idRegion = idRegion,
                                nameRegion = nameRegion
                            )
                        )
                    )
                }
                else -> {
                    regionsCountriesViewModel.setPlaceState(
                        PlaceState.Empty
                    )
                }
            }

        }


//        arguments?.getString(ARGS_COUNTRY_NAME)?.let { binding.inputCountry.setText(it) }
//        arguments?.getString(ARGS_REGION_NAME)?.let { binding.inputRegion.setText(it) }

        regionsCountriesViewModel.observePlaceState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.clickCountry.setOnClickListener(listener)
        binding.clickRegion.setOnClickListener(listener)
        binding.buttonBack.setOnClickListener(listener)
    }

    private fun render(state: PlaceState?) {
        Log.e("state", "state: PlaceState")
        when (state) {
            is PlaceState.ContentCountry -> {
                Log.e("state.country.name", "state.country.name ${state.country.name}")
                binding.inputCountry.setText(state.country.name)
            }

            is PlaceState.ContentPlace -> {
                binding.inputCountry.setText(state.place.nameCountry)
                binding.inputRegion.setText(state.place.nameRegion)
            }

            is PlaceState.Empty, null -> {
                binding.inputCountry.text?.clear()
                binding.inputRegion.text?.clear()
            }

            is PlaceState.Error -> {
                binding.inputCountry.text?.clear()
                binding.inputRegion.text?.clear()
            }
        }
    }

    private val listener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.clickCountry -> {
                    findNavController().navigate(R.id.action_placeFragment_to_countryFragment)
                }

                R.id.clickRegion -> {
                    findNavController().navigate(R.id.action_placeFragment_to_regionFragment)
                }

                R.id.buttonBack -> {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "PlaceFragment"
        private const val ARGS_COUNTRY_ID = "country_id"
        private const val ARGS_COUNTRY_NAME = "country_name"
        private const val ARGS_REGION_ID = "region_id"
        private const val ARGS_REGION_NAME = "region_name"

        fun createArgsCounty(countryId: String, countryName: String): Bundle =
            bundleOf(ARGS_COUNTRY_ID to countryId, ARGS_COUNTRY_NAME to countryName)

        fun createArgsRegion(countryId: String, countryName: String, regionId: String, regionName: String): Bundle =
            bundleOf(
                ARGS_COUNTRY_ID to countryId,
                ARGS_COUNTRY_NAME to countryName,
                ARGS_REGION_ID to regionId,
                ARGS_REGION_NAME to regionName
            )
    }
}
