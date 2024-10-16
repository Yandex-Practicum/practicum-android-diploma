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
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.RegionSelectFragmentBinding
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.areas.presentation.RegionSelectViewModel
import ru.practicum.android.diploma.filters.areas.ui.model.AreaSelectScreenState

class RegionSelectFragment : Fragment() {
    private var _binding: RegionSelectFragmentBinding? = null
    private val binding get() = _binding!!

    private val _adapter: RegionSelectRecyclerViewAdapter? = null
    private val adapter get() = _adapter!!

    private val viewModel by viewModel<RegionSelectViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegionSelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val regionSelectAdapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = regionSelectAdapter
    }

    @Suppress("UnusedPrivateMember")
    private fun render(state: AreaSelectScreenState) {
        when (state) {
            is AreaSelectScreenState.ChooseItem -> showContent(state.items)
            AreaSelectScreenState.Empty -> showEmpty()
            AreaSelectScreenState.NetworkError -> showNetworkError()
            AreaSelectScreenState.ServerError -> showServerError()
        }
    }

    private fun showContent(item: List<Area>) {
        binding.recyclerView.isVisible = true
        binding.emptyPlaceholder.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        adapter.list.clear()
        adapter.list.addAll(item)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.notFoundPlaceholder.isVisible = true
        binding.recyclerView.isVisible = false
        binding.emptyPlaceholder.isVisible = false
    }

    private fun showNetworkError() {
        binding.notFoundPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
        binding.emptyPlaceholder.isVisible = true
    }

    private fun showServerError() {
        binding.notFoundPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
        binding.emptyPlaceholder.isVisible = true
        binding.image.setImageResource(R.drawable.search_server_error_placeholder)
        binding.text.setText(R.string.server_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
