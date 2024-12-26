package ru.practicum.android.diploma.ui.filter.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterSettingsBinding
import ru.practicum.android.diploma.domain.models.Filter

class FilterSettingsFragment : Fragment() {

    private var _binding: FragmentFilterSettingsBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterSettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterSettingsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilteredUi(viewModel.currentFilter)

        binding.etCountry.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_choiceWorkplaceFragment)
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etIndustries.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_choiceIndustryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setFilteredUi(filter: Filter) {
        binding.etCountry.setText(filter.country?.name ?: "")
        binding.etIndustries.setText(filter.industry?.name ?: "")
        binding.etSalary.setText(if (filter.salary != null && filter.salary != 0) filter.salary.toString() else "")
        binding.checkBoxSalary.setChecked(filter.onlyWithSalary)

    }
}
