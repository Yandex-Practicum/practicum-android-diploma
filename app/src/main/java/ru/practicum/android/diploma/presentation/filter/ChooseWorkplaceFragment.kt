package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChooseWorkplaceBinding
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.presentation.filter.chooseArea.ChooseCountryViewModel

class ChooseWorkplaceFragment : Fragment() {

    private var _binding: FragmentChooseWorkplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChooseCountryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedCountry: Country? = viewModel.interactor.getSelectedCountry()
        val countryText: String = savedCountry?.name.orEmpty()

        binding.countryTextInputEditText.setText(countryText)

        binding.settingsBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.regionTextInputEditText.setOnClickListener {
            findNavController().navigate(R.id.action_chooseWorkplaceFragment_to_chooseAreaFragment)
        }

        binding.countryTextInputEditText.setOnClickListener {
            findNavController().navigate(R.id.action_chooseWorkplaceFragment_to_chooseCountryFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}