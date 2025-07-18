package ru.practicum.android.diploma.ui.filtering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilteringBinding
import ru.practicum.android.diploma.presentation.filtering.FilteringViewModel

class FilteringFragment : Fragment() {

    private var _binding: FragmentFilteringBinding? = null
    private val binding: FragmentFilteringBinding get() = _binding!!
    private val viewModel: FilteringViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilteringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarFiltering.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.choicePlaceWork.setOnClickListener {
            findNavController().navigate(R.id.action_to_place_work_fragment)
        }

        binding.choiceSectorWork.setOnClickListener {
            findNavController().navigate(R.id.action_to_sector_work_fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
