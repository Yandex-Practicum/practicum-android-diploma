package ru.practicum.android.diploma.ui.filter.workplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoiceWorkplaceBinding

class ChoiceWorkplaceFragment : Fragment() {

    private var _binding: FragmentChoiceWorkplaceBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceWorkplaceBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnIndustries.setOnClickListener {
//            findNavController().navigate(R.id.action_filterSettingsFragment_to_choiceIndustryFragment)
//        }

        binding.etCountry.setOnClickListener {
            findNavController().navigate(R.id.action_choiceWorkplaceFragment_to_choiceCountryFragment)
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
