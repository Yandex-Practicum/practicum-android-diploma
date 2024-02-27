package ru.practicum.android.diploma.ui.search

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.ui.search.adapter.PageVacancyAdapter
import ru.practicum.android.diploma.ui.search.adapter.SearchLoadStateAdapter

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vacancyList = binding.vacancyList
        val adapter = PageVacancyAdapter().apply {
            this.addLoadStateListener(viewModel::listener)

//            this.addLoadStateListener() { loadState ->
//                val errorState = when {
//                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
//                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
//                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
//                    else -> null
//                }
//                errorState?.let {
//                    //Можно взять тип ошибки из it.error
//                    //Toast.makeText(context, "Показываем ошибку", Toast.LENGTH_LONG).show()
//                }
//            }
        }

        vacancyList.adapter = adapter.withLoadStateFooter(
            footer = SearchLoadStateAdapter(),
        )

        vacancyList.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.stateVacancyData.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.searchState.collect {
                when (it) {
                    SearchState.FailedToGetList -> {
                        controlSearchButton()
                        binding.imageSearchNotStarted.gone()
                        binding.recyclerVacancyLayout.visible()
                        binding.vacancyList.gone()
                        binding.errorNoInternet.noInternetLayout.gone()
                        binding.errorFailedGetCat.errorFailedGetCat.visible()
                        binding.progressBar.gone()
                        binding.pagingProgressBar.gone()
                        binding.searchMessage.visible()
                        binding.searchMessage.text = "Таких вакансий нет"
                    }

                    SearchState.Loaded -> {
                        controlSearchButton()
                        binding.imageSearchNotStarted.gone()
                        binding.recyclerVacancyLayout.visible()
                        binding.errorNoInternet.noInternetLayout.gone()
                        binding.progressBar.gone()
                        binding.pagingProgressBar.gone()
                        binding.searchMessage.visible()
                        binding.searchMessage.text = "Найдено ${(it as SearchState.Loaded).counter} вакансий"

                    }

                    SearchState.Loading -> {
                        controlSearchButton()
                        binding.imageSearchNotStarted.gone()
                        binding.recyclerVacancyLayout.gone()
                        binding.errorNoInternet.noInternetLayout.gone()
                        binding.progressBar.visible()
                        binding.pagingProgressBar.gone()
                        binding.searchMessage.gone()
                    }

                    SearchState.NoInternet -> {
                        controlSearchButton()
                        binding.imageSearchNotStarted.gone()
                        binding.recyclerVacancyLayout.gone()
                        binding.errorNoInternet.noInternetLayout.visible()
                        binding.progressBar.gone()
                        binding.pagingProgressBar.gone()
                        binding.searchMessage.gone()
                    }

                    SearchState.Search -> {
                        controlSearchButton()
                        binding.imageSearchNotStarted.gone()
                        binding.recyclerVacancyLayout.gone()
                        binding.errorNoInternet.noInternetLayout.gone()
                        binding.progressBar.gone()
                        binding.pagingProgressBar.gone()
                        binding.searchMessage.gone()
                    }

                    SearchState.Start -> {
                        binding.searchButton.visible()
                        binding.clearButton.gone()
                        binding.imageSearchNotStarted.visible()
                        binding.recyclerVacancyLayout.gone()
                        binding.errorNoInternet.noInternetLayout.gone()
                        binding.progressBar.gone()
                        binding.pagingProgressBar.gone()
                        binding.searchMessage.gone()
                    }
                }
            }
        }
        binding.editText.addTextChangedListener {
            viewModel.search(it?.toString() ?: "")
        }

        binding.editText.addTextChangedListener(afterTextChanged = ::exampleFun)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.clearMessageAddPlayList()
            }
        }
    }

    fun exampleFun(it: Editable?) {
        viewModel.search(it?.toString() ?: "")
    }

    fun controlSearchButton() {
        if (binding.editText.text.isNullOrEmpty()) {
            binding.searchButton.visible()
            binding.clearButton.gone()
        } else {
            binding.searchButton.gone()
            binding.clearButton.visible()

        }
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
