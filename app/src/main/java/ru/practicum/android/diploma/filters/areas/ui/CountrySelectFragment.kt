package ru.practicum.android.diploma.filters.areas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.CountrySelectFragmentBinding
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.areas.ui.model.CountrySelectFragmentScreenState
import ru.practicum.android.diploma.filters.presentation.CountrySelectViewModel
import ru.practicum.android.diploma.filters.ui.presenter.AreasRecyclerViewAdapter

class CountrySelectFragment : Fragment() {
    private val viewModel by viewModel<CountrySelectViewModel>()
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

    private fun render(state: CountrySelectFragmentScreenState) {
        when (state) {
            is CountrySelectFragmentScreenState.ChooseItem -> showContent(state.items)
            CountrySelectFragmentScreenState.Empty -> showEmpty()
            CountrySelectFragmentScreenState.NetworkError -> showNetworkError()
            CountrySelectFragmentScreenState.ServerError -> showServerError()
        }
    }

    private fun showContent(item: List<Area>) {
        binding.recyclerView.isVisible
        adapter.list.clear()
        adapter.list.addAll(item as List<Area>)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        // Detekt
    }

    private fun showServerError() {
        // Detekt
    }

    private fun showNetworkError() {
        // Detekt
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
