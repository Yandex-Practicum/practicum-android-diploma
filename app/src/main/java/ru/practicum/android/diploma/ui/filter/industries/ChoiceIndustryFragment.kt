package ru.practicum.android.diploma.ui.filter.industries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.databinding.FragmentChoiceIndustryBinding

class ChoiceIndustryFragment : Fragment(), IndustriesAdapter.Listener {
    private var _binding: FragmentChoiceIndustryBinding? = null
    private val viewModel by viewModel<ChoiceIndustryViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceIndustryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foundedIndustryRv = binding.rvFoundedIndustry
        binding.rvFoundedIndustry.isVisible = true
        viewModel.showIndustries()

        viewModel.industriesState.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        val onItemClickListener: (IndustriesFullDto) -> Unit = {
        }

        val foundedIndustryAdapter = IndustriesAdapter(
            onItemClicked = onItemClickListener
        )
        foundedIndustryRv.adapter = foundedIndustryAdapter
    }

    private fun renderState(state: IndustriesState) {
        val foundedIndustryRv = binding.rvFoundedIndustry
        when (state) {
            is IndustriesState.FoundIndustries -> {
                val adapter = foundedIndustryRv.adapter as IndustriesAdapter
                adapter.updateIndustries(state.industries as List<IndustriesFullDto>)
                binding.rvFoundedIndustry.adapter = adapter
                binding.rvFoundedIndustry.isVisible = true
            }

            is IndustriesState.Error -> {
            }

            is IndustriesState.Loading -> {
            }

            is IndustriesState.NothingFound -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(industry: IndustriesFullDto) {
        TODO("Not yet implemented")
    }
}
