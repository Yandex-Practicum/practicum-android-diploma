package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.presentation.main.MainViewModel
import ru.practicum.android.diploma.ui.vacancy.VacanciesAdapter
import ru.practicum.android.diploma.util.SearchVacanciesState
import ru.practicum.android.diploma.util.setVacanciesCountText

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()
    private var vacanciesAdapter: VacanciesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacanciesAdapter = VacanciesAdapter(::onVacancyClicked)
        binding.mainRv.layoutManager = LinearLayoutManager(context)
        binding.mainRv.adapter = vacanciesAdapter

        binding.toolbarMain.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filtering -> {
                    findNavController().navigate(R.id.action_to_filtering_fragment)
                    true
                }

                else -> false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vacancies.collectLatest { vacancies ->
                    vacanciesAdapter?.submitList(vacancies)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    render(state)
                }
            }
        }
        binding.mainInputEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println(s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.mainInputEt.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_search_24,
                        0
                    )
                } else {
                    binding.mainInputEt.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.cross,
                        0
                    )
                    viewModel.onSearchTextChanged(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                println(s)
            }
        })

        binding.mainInputEt.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val queryText = binding.mainInputEt.text.toString()
                viewModel.performSearch(queryText)
                val inputMethodManager =
                    requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.mainInputEt.windowToken, 0)
                true
            } else {
                false
            }
        }

        binding.mainInputEt.setOnTouchListener(::handleTouchEvent)

        binding.mainRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                    firstVisibleItemPosition >= 0 && !viewModel.isLoadingNextPage.value) {
                    viewModel.searchNextPage()
                }
            }
        })
    }

    private fun onVacancyClicked(vacancyId: String) {
        val direction = MainFragmentDirections
            .actionToVacancyDetailsFragment(vacancyId = vacancyId)
        findNavController().navigate(direction)
    }

    private fun render(state: SearchVacanciesState) {
        when (state) {
            SearchVacanciesState.Default -> showDefault()
            SearchVacanciesState.Loading -> showLoading()
            SearchVacanciesState.NetworkError -> showNetworkError()
            SearchVacanciesState.NothingFound -> showNothingFound()
            is SearchVacanciesState.ShowContent -> showContent(state)
            SearchVacanciesState.NoInternet -> showNoInternet()
        }
    }

    fun showDefault() {
        binding.countVacancyTv.isVisible = false
        binding.mainRv.isVisible = false
        binding.mainPb.isVisible = false
        binding.mainDefaultIv.isVisible = true
        binding.errorImageAndMessageLl.isVisible = false

    }

    fun showLoading() {
        binding.countVacancyTv.isVisible = false
        binding.mainRv.isVisible = false
        binding.mainPb.isVisible = true
        binding.mainDefaultIv.isVisible = false
        binding.errorImageAndMessageLl.isVisible = false
    }

    fun showNoInternet() {
        binding.countVacancyTv.isVisible = false
        binding.mainRv.isVisible = false
        binding.mainPb.isVisible = false
        binding.mainDefaultIv.isVisible = false
        binding.errorImageAndMessageLl.isVisible = true
        binding.errorImageIv.setImageResource(R.drawable.image_no_internet)
        binding.errorTextTv.setText(R.string.there_is_no_internet_connection)
    }

    fun showNetworkError() {
        binding.countVacancyTv.isVisible = false
        binding.mainRv.isVisible = false
        binding.mainPb.isVisible = false
        binding.mainDefaultIv.isVisible = false
        binding.errorImageAndMessageLl.isVisible = true
        binding.errorImageIv.setImageResource(R.drawable.image_error_server)
        binding.errorTextTv.setText(R.string.server_error)
    }

    fun showNothingFound() {
        binding.countVacancyTv.isVisible = true
        binding.countVacancyTv.setText(R.string.no_such_vacancies)
        binding.mainRv.isVisible = false
        binding.mainPb.isVisible = false
        binding.mainDefaultIv.isVisible = false
        binding.errorImageAndMessageLl.isVisible = true
    }

    fun showContent(state: SearchVacanciesState.ShowContent) {
        binding.countVacancyTv.isVisible = true
        binding.countVacancyTv.setVacanciesCountText(state.content.found)
        binding.mainRv.isVisible = true
        binding.mainPb.isVisible = false
        binding.mainDefaultIv.isVisible = false
        binding.errorImageAndMessageLl.isVisible = false

    }

    override fun onDestroyView() {
        super.onDestroyView()
        vacanciesAdapter = null
        _binding = null
    }

    private fun handleTouchEvent(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP &&
            event.rawX >= binding.mainInputEt.right - binding.mainInputEt.compoundPaddingEnd
        ) {
            viewModel.onClearSearchClicked()
            binding.mainInputEt.text.clear()
            return true
        }
        return false
    }
}
