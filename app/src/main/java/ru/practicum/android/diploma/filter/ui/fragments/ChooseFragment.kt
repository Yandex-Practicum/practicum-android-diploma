package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreasBinding
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
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
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
        binding.progressBar.visibility = View.VISIBLE
    }

    protected open fun renderContent(list: List<Any?>) {
        with(binding) {
            placeholder.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun showNoData(message: String) {
        showMessage(message)
    }

    private fun showOffline(message: String) {
        showPlaceholder()
        showMessage(message)
    }

    private fun showError(message: String) {
        showPlaceholder()
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
    }

    private fun showPlaceholder() {
        binding.recycler.visibility = View.GONE
        binding.placeholder.visibility = View.VISIBLE
    }
}