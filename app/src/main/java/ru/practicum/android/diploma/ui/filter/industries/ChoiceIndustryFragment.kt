package ru.practicum.android.diploma.ui.filter.industries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentChoiceIndustryBinding
import ru.practicum.android.diploma.domain.models.Industries

class ChoiceIndustryFragment : Fragment(), IndustriesAdapter.Listener {
    private var _binding: FragmentChoiceIndustryBinding? = null
    private val viewModel by viewModel<ChoiceIndustryViewModel>()
    private val binding get() = _binding!!
    private var adapter: IndustriesAdapter? = null
    private var data: Industries? = null

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

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()

        }

        val onItemClickListener: (Industries) -> Unit = {
            binding.btEnter.isVisible = true
            data = it

        }

        val foundedIndustryAdapter = IndustriesAdapter(
            onItemClicked = onItemClickListener
        )
        foundedIndustryRv.adapter = foundedIndustryAdapter

        binding.etSearchIndustry.addTextChangedListener(
            afterTextChanged = { text ->
                binding.ibClearQuery.isVisible = !text.isNullOrEmpty()
                binding.ibClearSearch.isVisible = text.isNullOrEmpty()
                viewModel.searchDebounce(text.toString())
            }
        )

        binding.btEnter.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.ibClearQuery.setOnClickListener {
            binding.etSearchIndustry.setText("")
        }
    }

    private fun renderState(state: IndustriesState) {
        val foundedIndustryRv = binding.rvFoundedIndustry
        when (state) {
            is IndustriesState.FoundIndustries -> {
                adapter = foundedIndustryRv.adapter as IndustriesAdapter
                adapter?.updateIndustries(state.industries as List<Industries>)
                binding.rvFoundedIndustry.adapter = adapter
                binding.rvFoundedIndustry.isVisible = true
            }

            is IndustriesState.Error -> {

            }

            is IndustriesState.Loading -> {
            }

            is IndustriesState.NothingFound -> {
                adapter?.updateIndustries(emptyList())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(industry: Industries) {
        TODO("Not yet implemented")
    }
}
