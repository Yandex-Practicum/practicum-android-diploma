package ru.practicum.android.diploma.favourite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.favourite.presentation.models.FavoriteStateInterface
import ru.practicum.android.diploma.favourite.presentation.viewvodel.FavouriteViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.adapter.VacancyAdapter

class FragmentFavourite : BindingFragment<FragmentFavouriteBinding>() {

    private lateinit var vacancyAdapter: VacancyAdapter
    private lateinit var confirmDialogDeleteFavouriteVacancy: MaterialAlertDialogBuilder

    private val favouriteViewModel: FavouriteViewModel by viewModel()
    //private val vacancyId: Int? = null


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        setListeners()

        favouriteViewModel.observeStateFavourite().observe(viewLifecycleOwner){
            renderStateFavouriteVacancies(it)
        }

        //showConfirmDialog()
    }

    private fun initAdapter() {
        vacancyAdapter = VacancyAdapter(ArrayList<Vacancy>())
        binding.recyclerView.adapter = vacancyAdapter
    }

    private fun setListeners() {
        vacancyAdapter.itemClickListener = {position, vacancy ->

        }

        vacancyAdapter.itemLongClickListener = { position, vacancy ->
            showConfirmDialog(vacancy)
        }
    }

    private fun renderStateFavouriteVacancies(favoriteStateInterface: FavoriteStateInterface) {
        when(favoriteStateInterface){
            is FavoriteStateInterface.FavoriteVacanciesIsEmpty -> showPlaceHolder()
            is FavoriteStateInterface.FavoriteVacancies -> showFavoriteVacancies(favoriteStateInterface.favoriteVacancies)
        }
    }

    private fun showPlaceHolder(){
        binding.placeholderFavourite.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        vacancyAdapter.setVacancies(null)
    }

    private fun showFavoriteVacancies(vacancies: List<Vacancy>){
        binding.placeholderFavourite.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        vacancyAdapter.setVacancies(vacancies)
    }

    private fun showConfirmDialog(vacancy: Vacancy){
        confirmDialogDeleteFavouriteVacancy = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_vacancy)
            .setMessage("")
            .setPositiveButton(R.string.yes) { dialog, which ->
                favouriteViewModel.deleteTrack(vacancy)
            }
            .setNegativeButton(R.string.no) { dialog, which -> }

        confirmDialogDeleteFavouriteVacancy.show()
    }
}