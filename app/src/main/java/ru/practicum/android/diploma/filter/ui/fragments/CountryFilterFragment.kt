package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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


open class CountryFilterFragment : Fragment(R.layout.fragment_region_department) {

    protected open val fragment = "Country"
    protected val binding by viewBinding<FragmentRegionDepartmentBinding>()
    @Inject lateinit var filterAdapter: FilterAdapter
    @Inject lateinit var debouncer: Debouncer
    protected open val viewModel: CountryViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.hasUserData(fragment)
        initListeners()
        initAdapter()

        binding.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.log(thisName, "$text")
            viewModel.onSearchQueryChanged(text.toString())
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.uiState.collect { state ->
                viewModel.log("CountryFilterFragment", "state ${state.thisName}")
                when (state) {
                    is FilterScreenState.Default    -> renderDefault()
                    is FilterScreenState.Loading    -> showProgressBar()
                    is FilterScreenState.Content<*> -> renderContent(state.list)
                    is FilterScreenState.Offline    -> showMessage(state.message)
                    is FilterScreenState.NoData<*>  -> renderNoData(state.message)
                    is FilterScreenState.Error      -> showMessage(state.message)
                }
            }
        }
    }

    private fun renderContent(list: List<Any?>) {
        viewModel.log(thisName, "renderContent(${list.size}: List<Any?>)")
        binding.placeholderContainer.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
        refreshList(list)
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
    }

    private fun renderDefault() {
        viewModel.log(thisName, "renderDefault()")
        binding.placeholderContainer.visibility = View.GONE
        binding.recycler.visibility = View.GONE
    }

    private fun renderNoData(message: String) {
        viewModel.log(thisName, "renderNoData($message: String)")
        showMessage(message)
        binding.placeholderContainer.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }

    private fun showProgressBar() {
        //TODO("Not yet implemented")
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(requireActivity().getColor(R.color.blue))
            .setTextColor(requireActivity().getColor(R.color.white))
            .show()
    }

    private fun initListeners() {
        binding.filterToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        initAdapterListener()
    }

    protected open fun initAdapterListener() {
        filterAdapter.onClickCountry = { country ->
            viewModel.saveCountry(country)
            findNavController().navigate(
                CountryFilterFragmentDirections.actionCountryFilterFragmentToWorkPlaceFilterFragment(
                    country,
                    null
                )
            )
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

}