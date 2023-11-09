package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChooseWorkplaceBinding

class ChooseWorkplaceFragment : Fragment() {

    private var _binding: FragmentChooseWorkplaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.countryTextInputLayout.setOnClickListener {
            findNavController().navigate(R.id.action_chooseWorkplaceFragment_to_chooseCountryFragment)
        }

        binding.regionTextInputEditText.setOnClickListener {
            findNavController().navigate(R.id.action_chooseWorkplaceFragment_to_chooseAreaFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}