package ru.practicum.android.diploma.ui.filter.workplace.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
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

    @Suppress("detekt:LongMethod")
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
        bindTextWatcher()
        bindClearSearch()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeState().observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is RegionState.Content -> {
                            showContent()

                            adapter.regionList.clear()
                            adapter.regionList.addAll(state.regions)

                            adapter.notifyDataSetChanged()
                        }

                        is RegionState.Empty -> showEmpty(getString(state.message))
                        is RegionState.Error -> showError(getString(state.errorMessage))
                        is RegionState.Loading -> showLoading()
                    }
                }
            }
        }

        binding.regionToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContent() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.errorContainer.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_error_get_list)
        binding.errorTextView.text = errorMessage
        binding.progressBar.visibility = View.GONE
    }

    private fun showEmpty(message: String) {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_nothing_found)
        binding.errorTextView.text = message
        binding.progressBar.visibility = View.GONE
    }

    private fun bindTextWatcher() = with(binding) {
        search.addTextChangedListener(
            onTextChanged = { s, _, _, _ ->
                val text = s.toString()
                viewModel.search(text)

                if (text.isEmpty()) {
                    ivSearch.isVisible = true
                    ivCross.isVisible = false
                } else {
                    ivSearch.isVisible = false
                    ivCross.isVisible = true
                }
            }
        )
    }

    private fun bindClearSearch() = with(binding) {
        ivCross.setOnClickListener {
            search.text = null
        }
    }
}
