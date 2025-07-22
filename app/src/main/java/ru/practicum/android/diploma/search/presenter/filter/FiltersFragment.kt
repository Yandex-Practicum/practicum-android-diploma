package ru.practicum.android.diploma.search.presenter.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.search.domain.model.Industry

class FiltersFragment : Fragment() {
    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltersViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedIndustry.collect { industry ->
                updateIndustryField(industry)
            }
        }

        binding.backButtonId.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fieldId.setOnClickListener {
            if (viewModel.selectedIndustry.value == null) {
                findNavController().navigate(R.id.action_filtersFragment_to_fieldsFragment)
            } else {
                viewModel.updateIndustry(null)
            }
        }
    }

    private fun updateIndustryField(industry: Industry?) {
        if (industry != null) {
            binding.filterField.text = industry.name
            binding.filterField.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        } else {
            binding.filterField.text = getString(R.string.filter_field)
            binding.filterField.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
