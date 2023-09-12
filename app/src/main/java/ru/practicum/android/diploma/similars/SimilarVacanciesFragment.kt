package ru.practicum.android.diploma.similars

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.MainCompositeAdapter
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.VacancyAdapterDelegate
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class SimilarVacanciesFragment : Fragment(R.layout.fragment_similars_vacancy) {
    
    private val viewModel: SimilarVacanciesViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSimilarsVacancyBinding>()
    private val args by navArgs<SimilarVacanciesFragmentArgs>()
    
    private val vacancyAdapter by lazy {
        MainCompositeAdapter
            .Builder()
            .add(VacancyAdapterDelegate(onClick = { vacancy ->
                navigateToDetails(vacancy)
            }))
            .build()
    }
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated()")
        initAdapter()
        initListeners()
        collector()
        viewModel.getSimilarVacancies(args.id)
    }

    private fun collector() {
        viewModel.log(thisName, "collector()")
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                state.render(binding)
            }
        }
    }

    private fun initAdapter() {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = vacancyAdapter
    }

    private fun initListeners() {
        binding.similarVacancyToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun navigateToDetails(vacancy: Vacancy) {
        findNavController().navigate(
            SimilarVacanciesFragmentDirections.actionSimilarsVacancyFragmentToDetailsFragment(vacancy.id)
        )
    }
}