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
import ru.practicum.android.diploma.databinding.FragmentAreasBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.adapters.FilterAdapter
import ru.practicum.android.diploma.root.model.UiState.*
import ru.practicum.android.diploma.filter.ui.view_models.AreasViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

open class ChooseFragment : Fragment(R.layout.fragment_areas) {

    protected open val fragment = ""
    @Inject lateinit var debouncer: Debouncer
    @Inject lateinit var filterAdapter: FilterAdapter
    protected val binding by viewBinding<FragmentAreasBinding>()
    protected open val viewModel: AreasViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initAdapter()
        viewModel.getData()

        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log("CountryFilterFragment", "state ${state.thisName}")
                when (state) {
                    is Loading    -> showLoadingScreen()
                    is Content<*> -> renderContent(state.list)
                    is NoData     -> showNoData(state.message)
                    is Offline    -> showOffline(state.message)
                    is Error      -> showError(state.message)
                }
            }
        }
    }

    protected open fun showLoadingScreen() {
        val fragment = binding.thisName
        val context = binding.root.context
        with(binding) {
            if (fragment == thisName) toolbar.title = context.getString(R.string.choose_region)
            else toolbar.title = context.getString(R.string.choose_country)
            placeholder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun renderContent(list: List<Any?>) {
        viewModel.log("CountryFragment", "renderContent: list.size = ${list.size})")
        with(binding) {
            placeholder.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
        filterAdapter.countryList = list as List<Country>
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
    }

    protected open fun showNoData(message: String) {
        showMessage(message)
    }

    protected open fun showOffline(message: String) {
        showMessage(message)
    }

    protected open fun showError(message: String) {
        showMessage(message)
    }

    protected open fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initAdapter() {
        filterAdapter.fragment = fragment
        binding.recycler.adapter = filterAdapter
    }

    private fun showMessage(message: String) {
        val context = binding.root.context
        Snackbar
            .make(context, binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(context.getColor(R.color.blue))
            .setTextColor(context.getColor(R.color.white))
            .show()
        binding.placeholder.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }
}