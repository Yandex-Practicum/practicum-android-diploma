package ru.practicum.android.diploma.ui.region

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.AreaConverter.mapToCountry
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.ui.country.CountryAdapter

class RegionFragment : Fragment() {

    private val viewModel by viewModel<RegionViewModel>()
    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    private var regionId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CountryAdapter()
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

        binding.click.setOnClickListener {
            binding.edit.setText("")
        }

        binding.regionRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.adapter = adapter

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (binding.edit.text.isNotEmpty()) {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.close_icon)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                } else {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edit.text.isNotEmpty()) {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.close_icon)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                } else {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
                if (binding.edit.text.isNotEmpty()) {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.close_icon)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                } else {
                    val newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
                    binding.edit.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
                }
            }
        }

        binding.edit.addTextChangedListener(textWatcher)

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegionState.Content -> {
                    showContent()
                    adapter.countryList.clear()
                    adapter.filteredList.clear()
                    adapter.countryList.addAll(state.regionId.areas.map { it.mapToCountry() }.sortedBy { it.name })
                    adapter.filteredList.addAll(state.regionId.areas.map { it.mapToCountry() }.sortedBy { it.name })
                    adapter.notifyDataSetChanged()
                }

                is RegionState.Empty -> ""
                is RegionState.Error -> ""
                is RegionState.Loading -> showLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContent() {
        binding.regionRecycler.visibility = View.VISIBLE
        binding.regionProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.regionRecycler.visibility = View.GONE
        binding.regionProgressBar.visibility = View.VISIBLE
    }
}
