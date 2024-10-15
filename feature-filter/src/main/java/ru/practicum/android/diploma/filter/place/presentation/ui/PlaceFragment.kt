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

        regionsCountriesViewModel.initData()

        regionsCountriesViewModel.observePlaceState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.clickCountry.setOnClickListener(listener)
        binding.clickRegion.setOnClickListener(listener)
        binding.clickCountryClear.setOnClickListener(listener)
        binding.clickRegionClear.setOnClickListener(listener)
        binding.buttonBack.setOnClickListener(listener)
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
                binding.inputCountry.setText(state.country.name)
                binding.inputRegion.setText("")
                binding.clickCountryClear.visibility = View.VISIBLE
                binding.clickCountry.visibility = View.GONE
                binding.clickRegionClear.visibility = View.GONE
                binding.clickRegion.visibility = View.VISIBLE
                binding.selectButton.visibility = View.VISIBLE
            }

            is PlaceState.ContentPlace -> {
                placeInstance = state.place
                binding.inputCountry.setText(state.place.nameCountry)
                binding.inputRegion.setText(state.place.nameRegion)
                binding.clickCountryClear.visibility = View.VISIBLE
                binding.clickCountry.visibility = View.GONE
                binding.clickRegionClear.visibility = View.VISIBLE
                binding.clickRegion.visibility = View.GONE
                binding.selectButton.visibility = View.VISIBLE
            }

            is PlaceState.Empty, null -> {
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

            is PlaceState.Error -> {
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

                R.id.clickCountryClear -> {
                    placeInstance = Place(
                        idCountry = null,
                        nameCountry = null,
                        idRegion = null,
                        nameRegion = null
                    )
                    regionsCountriesViewModel.setPlaceState(PlaceState.Empty)
                    regionsCountriesViewModel.setPlaceInDataFilter(placeInstance)
                }

                R.id.clickRegionClear -> {
                    placeInstance = Place(
                        idCountry = placeInstance.idCountry,
                        nameCountry = placeInstance.nameCountry,
                        idRegion = placeInstance.idRegion,
                        nameRegion = placeInstance.nameRegion
                    )
                    regionsCountriesViewModel.setPlaceState(PlaceState.ContentCountry(
                        Country(
                            id = placeInstance.idCountry?:"",
                            name = placeInstance.nameCountry?:""
                        )
                    ))
                    regionsCountriesViewModel.setPlaceInDataFilter(
                        Place(
                            idCountry = placeInstance.idCountry,
                            nameCountry = placeInstance.nameCountry,
                            idRegion = null,
                            nameRegion = null
                        )
                    )
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
}
