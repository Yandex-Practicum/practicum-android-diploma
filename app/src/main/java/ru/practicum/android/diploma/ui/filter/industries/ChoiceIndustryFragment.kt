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
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry

class ChoiceIndustryFragment : Fragment(), IndustriesAdapter.Listener {
    private var _binding: FragmentChoiceIndustryBinding? = null
    private val viewModel by viewModel<ChoiceIndustryViewModel>()
    private val binding get() = _binding!!
    private var adapter: IndustriesAdapter? = null
    private var data: Industry? = null
    private var filterSharedPreferences: Filter? = null

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

        getFilter()

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val onItemClickListener: (Industry) -> Unit = {
            binding.btEnter.isVisible = true
            data = it
        }

        val foundedIndustryAdapter = IndustriesAdapter(
            onItemClicked = onItemClickListener,
            selectedPosition = data?.id
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
            val saveIndustryFilter = Filter(industry = data)
            viewModel.setFilter(saveIndustryFilter)
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
                adapter?.updateIndustries(state.industries as List<Industry>)
                binding.rvFoundedIndustry.adapter = adapter
                binding.rvFoundedIndustry.isVisible = true
            }

            is IndustriesState.NothingFound -> {
                adapter?.updateIndustries(emptyList())
            }
        }
    }

    private fun getFilter() {
        filterSharedPreferences = viewModel.getFilter()
        if (!filterSharedPreferences?.industry?.id.isNullOrEmpty()) {
            data = filterSharedPreferences?.industry
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(industry: Industry) {
        TODO("Not yet implemented")
    }
}
