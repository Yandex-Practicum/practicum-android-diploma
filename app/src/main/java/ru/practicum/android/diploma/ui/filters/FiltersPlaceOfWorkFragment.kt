package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterChoosePlaceOfWorkBinding

class FiltersPlaceOfWorkFragment : Fragment() {

    private var _binding: FragmentFilterChoosePlaceOfWorkBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterChoosePlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.arrowBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.country.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceOfWorkFragment_to_filtersCountryFragment)
        }

        binding.region.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceOfWorkFragment_to_filtersRegionFragment)
        }
    }
}
