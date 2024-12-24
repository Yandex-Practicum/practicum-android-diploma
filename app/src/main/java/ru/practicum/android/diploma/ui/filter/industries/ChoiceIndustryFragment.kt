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
import ru.practicum.android.diploma.domain.models.Vacancy

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

        binding.rvFoundedIndustry.isVisible = true
        viewModel.showIndustries()

        viewModel.industriesState.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
    }

    private fun renderState(state: IndustriesState) {
        val onItemClickListener: (IndustriesFullDto) -> Unit = {
            // Логика, исполняемая по нажатию на элемент списка вакансий
        }
        when (state) {
            is IndustriesState.FoundIndustries -> {
                val adapter = IndustriesAdapter(onItemClickListener)
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
