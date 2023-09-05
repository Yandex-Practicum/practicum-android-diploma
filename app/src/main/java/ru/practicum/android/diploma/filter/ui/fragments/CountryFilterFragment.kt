package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.adapters.CountryFilterAdapter
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject


open class CountryFilterFragment : Fragment(R.layout.fragment_country_filter) {

    protected open var _binding: FragmentCountryFilterBinding? = null
    private val binding get() = _binding
    @Inject lateinit var countryAdapter: CountryFilterAdapter
    private val viewModel: CountryViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryFilterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initAdapter()
        initAdapterListeners()

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "state ${state.thisName}")
                when (state) {
                    is FilterScreenState.Default -> renderDefault()
                    is FilterScreenState.Loading -> showProgressBar()
                    is FilterScreenState.Content -> renderContent(state.list)
                    is FilterScreenState.Offline -> showMessage(state.message)
                    is FilterScreenState.NoData  -> renderNoData(state.message)
                    is FilterScreenState.Error   -> showMessage(state.message)
                }
            }
        }
    }

    protected open fun renderContent(list: List<Country>) {
        binding.placeholderContainer.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
        countryAdapter.countriesList = list
        countryAdapter.notifyDataSetChanged()
    }

    protected open fun renderDefault() {
        binding.placeholderContainer.visibility = View.GONE
        binding.recycler.visibility = View.GONE
    }

    protected open fun renderNoData(message: String) {
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
    }

    protected open fun initAdapterListeners() {
        countryAdapter.onItemClick = { country ->
            findNavController().navigate(
                CountryFilterFragmentDirections.actionCountryFilterFragmentToWorkPlaceFilterFragment(
                    country,
                    null
                )
            )
        }
    }

    private fun initAdapter() {
        binding.recycler.adapter = countryAdapter
    }

}