package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.CountrySelectFragmentBinding
import ru.practicum.android.diploma.filters.domain.models.Area
import ru.practicum.android.diploma.filters.presentation.CountrySelectViewModel
import ru.practicum.android.diploma.filters.presentation.FiltersChooserScreenState
import ru.practicum.android.diploma.filters.ui.presenter.AreasRecyclerViewAdapter

class CountrySelectFragment : Fragment() {
    private val viewModel: CountrySelectViewModel by viewModel()
    private var _binding: CountrySelectFragmentBinding? = null
    private val binding get() = _binding!!

    private var _adapter: AreasRecyclerViewAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountrySelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = AreasRecyclerViewAdapter {
            onAreaClick(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.recyclerView.adapter = adapter

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun onAreaClick(area: Area) {
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
        Log.d("T", "ZAEBISKA ${item}")
        binding.recyclerView.isVisible
        adapter.list.clear()
        adapter.list.addAll(item as List<Area>)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
    }

    private fun showServerError() {
    }

    private fun showNetworkError() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
