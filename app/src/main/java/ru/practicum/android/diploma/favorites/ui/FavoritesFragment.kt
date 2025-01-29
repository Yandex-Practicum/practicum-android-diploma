package ru.practicum.android.diploma.favorites.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.presentation.state.FavoritesScreenState
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoriteScreenViewModel
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.vacancy.ui.VacancyFragment

class FavoritesFragment : Fragment(), VacancyViewHolder.OnItemClickListener {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoriteScreenViewModel>()
    private var vacancyList: ArrayList<VacancyItems> = arrayListOf()
    private val vacancyAdapter = VacancyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacancyAdapter.onItemClickListener = this

        val rvItems: RecyclerView = binding.rvFavoriteItems
        rvItems.apply {
            adapter = vacancyAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        vacancyAdapter.items = vacancyList

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoritesScreenState.Content -> {
                    showContent(state.data)
                }

                is FavoritesScreenState.Error -> {
                    showError(state.message)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showError(message: String) {
        vacancyList.clear()
        vacancyAdapter.notifyDataSetChanged()

        val placeholderImage: ImageView = binding.placeholderImage
        val placeholderText: TextView = binding.placeholderText
        val placeholderLayout: LinearLayout = binding.placeholder
        val recyclerView: RecyclerView = binding.rvFavoriteItems

        placeholderLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        if (message == getString(R.string.no_vacancies_found_text_hint)) {
            placeholderImage.setImageResource(R.drawable.failed_to_load_vacancies_ph)
        } else {
            placeholderImage.setImageResource(R.drawable.list_is_empty)
        }

        placeholderText.text = message
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(data: List<VacancyItems>) {
        if (data.isEmpty()) {
            showError(getString(R.string.list_is_empty))
        } else {
            val recyclerView: RecyclerView = binding.rvFavoriteItems
            val placeholderLayout: LinearLayout = binding.placeholder

            recyclerView.apply {
                adapter = vacancyAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            vacancyList.clear()
            vacancyList.addAll(data)

            vacancyAdapter.items = vacancyList
            vacancyAdapter.notifyDataSetChanged()

            placeholderLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: VacancyItems) {
        showVacancyDetails(item.id)
    }

    private fun showVacancyDetails(vacancyId: String) {
        findNavController().navigate(
            R.id.action_favoritesFragment_to_vacancyFragment,
            VacancyFragment.createArgs(vacancyId)
        )
    }

}
