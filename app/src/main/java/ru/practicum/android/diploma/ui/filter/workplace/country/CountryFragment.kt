package ru.practicum.android.diploma.ui.filter.workplace.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.ui.filter.workplace.country.adapter.CountryAdapter
import ru.practicum.android.diploma.ui.filter.workplace.region.RegionState

class CountryFragment : Fragment() {

    private val viewModel by viewModel<CountryViewModel>()

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCountryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CountryAdapter()
        adapter.itemClickListener = { _, item ->
            viewModel.setCountryInfo(
                CountryShared(
                    countryId = item.id,
                    countryName = item.name
                )
            )
            findNavController().popBackStack()
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Content -> {
                    showContent()

                    adapter.countryList.addAll(state.region)
                    //TODO Отсортировать список стран, как указано в макете
                    adapter.notifyDataSetChanged()
                }

                is CountryState.Empty -> showEmpty(getString(state.message))
                is CountryState.Error -> showError(getString(state.errorMessage))
                is CountryState.Loading -> showLoading()
            }
        }

        binding.countryToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.countryToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
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
}
