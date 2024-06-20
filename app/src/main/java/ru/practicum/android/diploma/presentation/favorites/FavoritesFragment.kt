package ru.practicum.android.diploma.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.favorites.FavoritesVacancyViewState
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.presentation.search.VacancyAdapter
import ru.practicum.android.diploma.util.VACANCY_KEY

class FavoritesFragment : Fragment(), VacancyAdapter.ItemVacancyClickInterface {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModel<FavoritesViewModel>()
    private var favoriteVacancyAdapter: VacancyAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewState()

        favoriteVacancyAdapter = VacancyAdapter()
        favoriteVacancyAdapter?.setInItemVacancyClickListener(this)
        binding.favoriteRecyclerView.adapter = favoriteVacancyAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        vm.getAllFavoritesVacancy()
    }

    fun observeViewState() {
        vm.favoriteVacancyScreenState.observe(viewLifecycleOwner) {
            when (it) {
                is FavoritesVacancyViewState.FavoritesVacancyError -> {
                    setFavoritesVacancyError()
                }

                is FavoritesVacancyViewState.FavoritesVacancyEmptyDataResult -> {
                    setFavoriteVacancyEmptyResult()
                }

                is FavoritesVacancyViewState.FavoritesVacancyDataResult -> {
                    setFavoriteVacancyData(it)
                }

                else -> { // это пустой метод
                }
            }
        }
    }

    private fun setFavoriteVacancyData(it: FavoritesVacancyViewState.FavoritesVacancyDataResult) {
        binding.favoritePlaceholderLayout.visibility = View.INVISIBLE
        binding.favoriteRecyclerView.visibility = View.VISIBLE
        favoriteVacancyAdapter?.setVacancyList(ArrayList(it.listOfFavoriteDomainVacancy))
    }

    private fun setFavoriteVacancyEmptyResult() {
        binding.favoritePlaceholderLayout.visibility = View.VISIBLE
        binding.favoriteRecyclerView.visibility = View.INVISIBLE
        Glide.with(this)
            .load(R.drawable.placeholder_empty_list)
            .centerCrop()
            .into(binding.favoritePlaceholderImage)
        binding.favoritePlaceholderMessage.text = this.getString(R.string.empty_list)
    }

    private fun setFavoritesVacancyError() {
        binding.favoritePlaceholderLayout.visibility = View.VISIBLE
        binding.favoriteRecyclerView.visibility = View.INVISIBLE
        Glide.with(this)
            .load(R.drawable.placeholder_cat)
            .centerCrop()
            .into(binding.favoritePlaceholderImage)
        binding.favoritePlaceholderMessage.text = this.getString(R.string.count_get_list_of_vacancies)
    }

    fun goToVacancyFragment(vacancy: DomainVacancy) {
        val bundle = Bundle()
        bundle.putParcelable(VACANCY_KEY, vacancy)
        findNavController().navigate(R.id.action_favoritesFragment_to_vacancyFragment, bundle)
    }

    override fun onItemVacancyClick(vacancy: DomainVacancy) {
        goToVacancyFragment(vacancy)
    }
}
