package ru.practicum.android.diploma.ui.filter.workplace.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountry
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.ui.filter.workplace.region.adapter.RegionAdapter

class RegionFragment : Fragment() {

    private val viewModel by viewModel<RegionViewModel>()

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RegionAdapter()
        adapter.itemClickListener = { _, item ->
            viewModel.setRegionInfo(
                RegionShared(
                    regionId = item.id,
                    regionParentId = item.parentId,
                    regionName = item.name
                )
            )
            findNavController().popBackStack()
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeState().observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is RegionState.Content -> {
                            showContent()

                            adapter.regionList.clear()
                            adapter.regionList.addAll(
                                state.regionId.areas.map { it.mapToCountry() }
                                    .sortedBy { it.name }
                            )

                            adapter.notifyDataSetChanged()
                        }

                        is RegionState.Empty -> showEmpty(getString(state.message))
                        is RegionState.Error -> showError(getString(state.errorMessage))
                        is RegionState.Loading -> showLoading()
                    }
                }
            }
        }

        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContent() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.errorContainerLinearLayout.visibility = View.GONE
        binding.regionProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainerLinearLayout.visibility = View.GONE
        binding.regionProgressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainerLinearLayout.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_error_get_list)
        binding.errorTextView.text = errorMessage
        binding.regionProgressBar.visibility = View.GONE
    }

    private fun showEmpty(message: String) {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainerLinearLayout.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_nothing_found)
        binding.errorTextView.text = message
        binding.regionProgressBar.visibility = View.GONE
    }
}
