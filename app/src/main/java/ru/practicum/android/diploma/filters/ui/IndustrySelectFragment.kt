package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.BranchSelectFragmentBinding
import ru.practicum.android.diploma.filters.domain.models.Industry
import ru.practicum.android.diploma.filters.presentation.FiltersChooserScreenState
import ru.practicum.android.diploma.filters.presentation.IndustriesFilterViewModel
import ru.practicum.android.diploma.filters.ui.presenter.IndustriesRecyclerViewAdapter

class IndustrySelectFragment : Fragment() {

    private var _adapter: IndustriesRecyclerViewAdapter? = null
    private val adapter get() = _adapter!!

    private var _binding: BranchSelectFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<IndustriesFilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BranchSelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = IndustriesRecyclerViewAdapter {
            onIndustryClick(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.recyclerView.adapter = adapter

        viewModel.getStateLiveData().observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onIndustryClick(industry: Industry) {
        // Коммент костыль
    }

    private fun render(state: FiltersChooserScreenState) {
        when (state) {
            is FiltersChooserScreenState.ChooseItem<*> -> showContent(state.items)
            FiltersChooserScreenState.Empty -> showEmpty()
            FiltersChooserScreenState.NetworkError -> showNetworkError()
            FiltersChooserScreenState.ServerError -> showServerError()
        }
    }

    private fun showContent(item: List<*>) {
        binding.emptyPlaceholder.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        binding.recyclerView.isVisible = true
        adapter.list.clear()
        adapter.list.addAll(item as List<Industry>)
        adapter.notifyDataSetChanged()

    }

    private fun showNetworkError() {
        binding.emptyPlaceholder.isVisible = false
        binding.notFoundPlaceholder.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun showServerError() {
        binding.emptyPlaceholder.isVisible = false
        binding.notFoundPlaceholder.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun showEmpty() {
        binding.emptyPlaceholder.isVisible = true
        binding.notFoundPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
    }
}
