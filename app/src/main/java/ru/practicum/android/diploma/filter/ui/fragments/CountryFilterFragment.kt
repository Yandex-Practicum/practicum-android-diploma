package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionDepartmentBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.adapters.FilterAdapter
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject
import kotlin.concurrent.timer


open class CountryFilterFragment : Fragment(R.layout.fragment_region_department) {

    protected open val fragment = COUNTRY
    @Inject lateinit var filterAdapter: FilterAdapter
    @Inject lateinit var debouncer: Debouncer
    protected val binding by viewBinding<FragmentRegionDepartmentBinding>()
    protected open val viewModel: CountryViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData()
        initListeners()
        initAdapter()

        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log("CountryFilterFragment", "state ${state.thisName}")
                when (state) {
                    is FilterScreenState.Default -> renderDefault()
                    is FilterScreenState.Loading -> showProgressBar()
                    is FilterScreenState.Content<*> -> renderContent(state.list)
                    is FilterScreenState.Offline -> showMessage(state.message)
                    is FilterScreenState.NoData<*> -> renderNoData(state.message)
                    is FilterScreenState.Error -> showMessage(state.message)
                }
            }
        }
    }

    private fun renderContent(list: List<Any?>) {
        viewModel.log(thisName, "renderContent(${list.size}: List<Any?>)")
        binding.placeholderContainer.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        refreshList(list)
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
    }

    private fun renderDefault() {
        viewModel.log(thisName, "renderDefault()")

        with(binding) {
            if (fragment == COUNTRY)
                toolbar.title = getString(R.string.choose_country)
            else
                toolbar.title = getString(R.string.choose_region)
            placeholderContainer.visibility = View.GONE
            recycler.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun renderNoData(message: String) {
        viewModel.log(thisName, "renderNoData($message: String)")
        showMessage(message)
        binding.placeholderContainer.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }

    private fun showProgressBar() {
        viewModel.log(thisName, "showProgressBar()")
    }

    private fun showMessage(message: String) {
        viewModel.log(thisName, "showMessage($message: String)")
        Snackbar
            .make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(requireActivity().getColor(R.color.blue))
            .setTextColor(requireActivity().getColor(R.color.white))
            .show()
    }

    private fun initListeners() {
        binding.filterToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        initAdapterListener()
    }

    protected open fun initAdapterListener() {
        filterAdapter.onClickCountry = { country ->
            viewModel.saveCountry(country)
            findNavController().navigateUp()
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun refreshList(list: List<Any?>) {
        filterAdapter.countryList = list as List<Country>
    }

    private fun initAdapter() {
        filterAdapter.fragment = fragment
        binding.recycler.adapter = filterAdapter
    }

    companion object {
        const val COUNTRY = "Country"
    }
}