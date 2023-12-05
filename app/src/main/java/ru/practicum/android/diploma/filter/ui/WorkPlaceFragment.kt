package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding

class WorkPlaceFragment : Fragment(R.layout.fragment_work_place) {

    private var _binding: FragmentWorkPlaceBinding? = null
    private val binding get() = _binding!!

//    private val viewModel by viewModel<FilterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkPlaceBinding.bind(view)

        binding.gotoCountryChooserFragmentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_workPlaceFragment_to_countryChooserFragment)
        }
        binding.gotoRegionChooserFragmentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_workPlaceFragment_to_areaChooserFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
