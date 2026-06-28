package ru.practicum.android.diploma.ui.fragments.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.viewmodels.IndustryState
import ru.practicum.android.diploma.presentation.viewmodels.IndustryViewModel
import ru.practicum.android.diploma.presentation.viewmodels.SearchViewModel
import ru.practicum.android.diploma.ui.adapter.VacancyAdapter
import ru.practicum.android.diploma.ui.fragments.IndustryAdapter
import ru.practicum.android.diploma.util.ViewStateHelper
import kotlin.getValue

class IndustrySelectionFragment : Fragment() {
    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IndustryViewModel by viewModel()
    private var adapter: IndustryAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.observeLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        adapter = IndustryAdapter()
        binding.industryList.adapter = adapter
        binding.industryList.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )

        // Установка кнопки "Назад"
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun render(state: IndustryState) {
        when (state) {
            is IndustryState.IsLoading -> showLoading()
            is IndustryState.Error -> showError()
            is IndustryState.Empty -> showEmpty()
            is IndustryState.Content -> showContent(state.industry)
        }
    }

    fun showContent(found: List<Industry>) {
        binding.industryList.visibility = View.VISIBLE
        adapter?.industrys = found
        adapter?.notifyDataSetChanged()
        binding.layoutServerError.root.visibility = View.GONE
        binding.layoutNoFound.root.visibility = View.GONE
        binding.layoutNoInternet.root.visibility = View.GONE
        binding.layoutLoading.root.visibility = View.GONE
    }

    fun showError() {
        binding.industryList.visibility = View.GONE
        binding.layoutServerError.root.visibility = View.VISIBLE
        binding.layoutNoFound.root.visibility = View.GONE
        binding.layoutNoInternet.root.visibility = View.GONE
        binding.layoutLoading.root.visibility = View.GONE
    }

    fun showEmpty() {
        binding.industryList.visibility = View.GONE
        binding.layoutServerError.root.visibility = View.GONE
        binding.layoutNoFound.root.visibility = View.VISIBLE
        binding.layoutNoInternet.root.visibility = View.GONE
        binding.layoutLoading.root.visibility = View.GONE
    }

    fun showLoading() {
        binding.industryList.visibility = View.GONE
        binding.layoutServerError.root.visibility = View.GONE
        binding.layoutNoFound.root.visibility = View.GONE
        binding.layoutNoInternet.root.visibility = View.GONE
        binding.layoutLoading.root.visibility = View.VISIBLE
    }


    companion object {
        const val INDUSTRY_ID_KEY = "INDUSTRY_ID_KEY"

        fun createArgs(vacancyId: String): Bundle =
            bundleOf(INDUSTRY_ID_KEY to vacancyId)
    }
}
