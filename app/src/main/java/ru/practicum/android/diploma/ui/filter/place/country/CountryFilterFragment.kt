package ru.practicum.android.diploma.ui.filter.place.country

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.ui.filter.place.country.adapters.CountryAdapter
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.CountryState
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.util.COUNTRY_KEY
import ru.practicum.android.diploma.util.REGION_KEY
import ru.practicum.android.diploma.util.debounce

class CountryFilterFragment : BindingFragment<FragmentCountryFilterBinding>() {
    private val viewModel: CountryViewModel by viewModel()

    private var adapter: CountryAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCountryFilterBinding {
        return FragmentCountryFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onClickCountryDebounce = debounce<Country>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { country ->
            onClickCountry(country)
        }

        adapter = CountryAdapter(
            object : CountryAdapter.CountryClickListener {
                override fun onCountryClick(country: Country) {
                    onClickCountryDebounce(country)
                }
            }
        )

        binding.countryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.countryRecyclerView.adapter = adapter

        viewModel.observeState.observe(viewLifecycleOwner) {
            when (it) {
                is CountryState.Content -> showCountries(it.countries)
                is CountryState.Empty -> showEmpty()
                is CountryState.Error -> showError(it.error)
                is CountryState.Loading -> showLoading()
            }
        }

        // Системная кнопка или жест назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment(false)
        }

        initUiTopbar()
    }

    private fun initUiTopbar() {
        binding.topbar.apply {
            btnFirst.setImageResource(R.drawable.arrow_back_24px)
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.country)
        }

        binding.topbar.btnFirst.setOnClickListener {
            closeFragment(false)
        }
    }

    private fun closeFragment(barVisibility: Boolean) {
        (activity as RootActivity).setNavBarVisibility(barVisibility)
        findNavController().popBackStack()

    }

    private fun onClickCountry(country: Country) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(COUNTRY_KEY, country)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(REGION_KEY, null)
        findNavController().popBackStack()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showCountries(countries: List<Country>) {
        adapter?.countries?.clear()
        adapter?.countries?.addAll(countries)
        adapter?.notifyDataSetChanged()
        binding.includedProgressBar.progressBar.isVisible = false
        binding.placeholder.isVisible = false
        binding.countryRecyclerView.isVisible = true
    }

    private fun showEmpty() {
        binding.countryRecyclerView.isVisible = false
        binding.includedProgressBar.progressBar.isVisible = false
        loadPlaceholder(R.drawable.err_load_list, R.string.no_get_list)
        binding.placeholder.isVisible = true
    }

    private fun showError(error: Int) {
        binding.countryRecyclerView.isVisible = false
        binding.includedProgressBar.progressBar.isVisible = false
        loadPlaceholder(R.drawable.err_server_1, R.string.server_error)
        binding.placeholder.isVisible = true
    }

    private fun showLoading() {
        binding.placeholder.isVisible = false
        binding.includedProgressBar.progressBar.isVisible = true
        binding.countryRecyclerView.isVisible = false
    }

    private fun loadPlaceholder(resourceIdImage: Int, resourceIdText: Int) {
        Glide.with(requireContext())
            .load(resourceIdImage)
            .placeholder(R.drawable.err_empty_list)
            .into(binding.imagePlaceholder)
        binding.textPlaceholder.text = resources.getString(resourceIdText)
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
