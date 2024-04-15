package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.ui.details.DetailsFragment

class FavoriteVacanciesFragment : Fragment() {

    private val viewModel by viewModel<FavoriteVacanciesViewModel>()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val data = ArrayList<VacancyDetails>()
    private var recyclerView: RecyclerView? = null

    private fun showEmptyVacancyList() {
        binding.favoriteItemsRecyclerView.visibility = View.INVISIBLE
        binding.emptyListFrame.visibility = View.VISIBLE
        binding.getListErrorFrame.visibility = View.INVISIBLE
    }

    private fun showGetVacanciesError() {
        binding.favoriteItemsRecyclerView.visibility = View.INVISIBLE
        binding.emptyListFrame.visibility = View.INVISIBLE
        binding.getListErrorFrame.visibility = View.VISIBLE
    }

    private fun showVacancyList() {
        binding.favoriteItemsRecyclerView.visibility = View.VISIBLE
        binding.emptyListFrame.visibility = View.INVISIBLE
        binding.getListErrorFrame.visibility = View.INVISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.favoriteItemsRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = FavoriteVacanciesRecyclerViewAdapter(data).apply {
            vacancyClicked = {
                // передача :Vacancy в фрагмент ДеталиВакансии
                findNavController().navigate(
                    R.id.action_favoriteFragment_to_fragmentDetails,
                    bundleOf(DetailsFragment.vacancyIdKey to it.id)
                )
            }
        }

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoriteVacanciesState.EmptyVacancyList -> {
                    showEmptyVacancyList()
                }

                is FavoriteVacanciesState.VacanciesError -> {
                    showGetVacanciesError()
                }

                is FavoriteVacanciesState.VacancyList -> {
                    showVacancyList()
                    data.clear()
                    data.addAll(state.vacancies.reversed())
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadFavoriteVacancies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
