package ru.practicum.android.diploma.filter.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterRegionBinding
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.RegionViewState
import ru.practicum.android.diploma.filter.presentation.viewmodel.RegionFilterViewModel
import ru.practicum.android.diploma.filter.ui.adapters.RegionAdapter

class FilterRegionFragment : Fragment() {
    private var _binding: FragmentFilterRegionBinding? = null
    private val binding get() = _binding!!
    private val regionViewModel by viewModel<RegionFilterViewModel>()
    private var regionListAdapter: RegionAdapter? = null
    private var textWatcher: TextWatcher? = null
    private var inputMethodManager: InputMethodManager? = null
    private var fadeInAnimation: Animation? = null
    private var fadeOutAnimation: Animation? = null
    private var adapterAnimationJob: Job? = null
    private var textInput: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        regionListAdapter = null
        textWatcher?.let { binding.textInput.removeTextChangedListener(it) }
        inputMethodManager = null
        adapterAnimationJob = null
        fadeInAnimation = null
        fadeOutAnimation = null
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRegionScreen()
    }

    private fun initializeRegionScreen() {
        setRecyclerView()
        setTextInput()
        setRegionListAnimation()
        initializeRegionList()
        setRegionListLiveDataObserver()
        setOnClickListeners()
    }

    private fun initializeRegionList() {
        regionViewModel.loadRegions()
    }

    private fun setRecyclerView() {
        with(binding) {
            regionListAdapter = RegionAdapter(::applyRegionToFilter)
            rvRegionList.adapter = regionListAdapter
        }
    }

    private fun setTextInput() {
        inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, ncout: Int) {
                with(binding) {
                    if (textInput.hasFocus() && s?.isEmpty() == true) {
                        progressBar.isVisible = false
                        noFoundPH.isVisible = false
                        serverErrorPH.isVisible = false
                    }
                }
                textInput = s.toString()
                regionViewModel.searchDebounce(textInput)
                Log.d("TextInput", "$textInput")
                binding.clearIcon.visibility = clearButtonVisibility(s)
                binding.searchIcon.visibility = searchIconVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("AfterTextChanged", "$s")
            }
        }
        binding.textInput.addTextChangedListener(textWatcher)

        setClearIconOnClickListener()
        setOnEditorActionListener()
    }

    private fun setClearIconOnClickListener() {
        binding.clearIcon.setOnClickListener {
            onClearIconPressed()
            inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    private fun setOnEditorActionListener() {
        binding.textInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                regionViewModel.declineLastSearch()
                regionViewModel.searchRegions(textInput)
                true
            }
            false
        }
    }

    private fun setRegionListLiveDataObserver() {
        regionViewModel.observeState().observe(viewLifecycleOwner) { state ->
            Log.d("RegionFragmentScreenState", "$state")
            renderState(state)
        }
    }

    private fun setOnClickListeners() {
        binding.topBar.setOnClickListener {
            findNavController().navigate(
                R.id.action_filterRegionFragment_to_filterPlaceOfWorkFragment
            )
        }
    }

    private fun renderState(state: RegionViewState) {
        when (state) {
            is RegionViewState.Loading -> showLoading()
            is RegionViewState.Success -> showContent(state)
            is RegionViewState.NotFoundError -> showNothingFoundPH()
            is RegionViewState.ServerError -> showServerErrorPH()
            else -> {}
        }
    }

    private fun showServerErrorPH() {
        with(binding) {
            serverErrorPH.isVisible = true
            noFoundPH.isVisible = false
            rvRegionList.isVisible = false
            progressBar.isVisible = false
        }
    }

    private fun showNothingFoundPH() {
        with(binding) {
            noFoundPH.isVisible = true
            rvRegionList.isVisible = false
            progressBar.isVisible = false
            serverErrorPH.isVisible = false
        }
    }

    private fun showContent(state: RegionViewState.Success) {
        with(binding) {
            Log.d("StateAdapter", "${state.areas}")
            regionListAdapter?.updateItems(state.areas)
            rvRegionList.isVisible = true
            progressBar.isVisible = false
            serverErrorPH.isVisible = false
            noFoundPH.isVisible = false
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            rvRegionList.isVisible = false
            serverErrorPH.isVisible = false
            noFoundPH.isVisible = false
        }
    }

    private fun onClearIconPressed() {
        with(binding) {
            regionViewModel.declineLastSearch()
            textInput.setText("")
            noFoundPH.isVisible = false
            serverErrorPH.isVisible = false
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun searchIconVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setRegionListAnimation() {
        fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
    }

    private fun applyRegionToFilter(area: Area) {
        regionViewModel.applyRegionToFilter(area)
        findNavController().navigate(
            R.id.action_filterRegionFragment_to_filterPlaceOfWorkFragment
        )
    }
}
