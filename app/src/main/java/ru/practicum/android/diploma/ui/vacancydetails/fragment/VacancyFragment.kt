package ru.practicum.android.diploma.ui.vacancydetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancydetails.state.VacancyDetailsScreenState
import ru.practicum.android.diploma.ui.vacancydetails.viewmodel.VacancyViewModel
import kotlin.properties.Delegates

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private var vacancyId by Delegates.notNull<Long>()

    /*
    переменная-индиктор: если из предыдущего экрана (Поиск или Избранное) она получает значение true,
    значит запрос делаем только в БД,
    иначе -> идем в сеть, потом идем в БД проверить она в избранном или нет -> обновить иконку избранного
     */
    private var isFromFavouritesScreen: Boolean = false

    private val viewModel: VacancyViewModel by viewModel { parametersOf(vacancyId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getVacancyId()
        setupListeners()
        observeViewModel()
    }

    private fun getVacancyId() {
        vacancyId = requireArguments().getLong(ARGS_VACANCY_ID)
        isFromFavouritesScreen = requireArguments().getBoolean(FROM_FAVOURITES_SCREEN)
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        // Этот метод нужно вынести в data-слой

//        binding.imageViewSharing.setOnClickListener {
//            viewModel.vacancy.value?.let { vacancy ->
//                val shareText = "${vacancy.name ?: "Вакансия"}\n\n${vacancy.alternateUrl ?: ""}"
//                val shareIntent = Intent(Intent.ACTION_SEND).apply {
//                    type = "text/plain"
//                    putExtra(Intent.EXTRA_TEXT, shareText)
//                }
//                startActivity(Intent.createChooser(shareIntent, ""))
//            }
//        }

        binding.imageViewFavorites.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteIcon(isFavorite)
        }

        viewModel.vacancyDetailsScreenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val resId = if (isFavorite) {
            R.drawable.ic_like_on_24
        } else {
            R.drawable.ic_like_off_24
        }
        binding.imageViewFavorites.setImageResource(resId)
    }

    private fun render(state: VacancyDetailsScreenState) {
        when (state) {
            is VacancyDetailsScreenState.Loading -> showLoading()
            is VacancyDetailsScreenState.ServerError -> showServerErrorPlaceholder()
            is VacancyDetailsScreenState.NotFoundError -> showNotFoundPlaceholder()
            is VacancyDetailsScreenState.Content -> showVacancyDetails(state.vacancy)
        }
    }

    private fun showLoading() {
        // Делаем видимым только прогрессбар
    }

    private fun showServerErrorPlaceholder() {
        // Делаем видимым только плейсхолдер
    }

    private fun showNotFoundPlaceholder() {
        // Делаем видимым только плейсхолдер
    }

    private fun showVacancyDetails(vacancy: Vacancy) {
        bindData(vacancy)
    }

    private fun bindData(vacancy: Vacancy) {
        // Здесь биндим данные в соответствующие поля
    }

    // Временная заглушка + fix detekt
    companion object {
        // private const val DEFAULT_VACANCY_ID: Long = 123L
        private const val ARGS_VACANCY_ID = "vacancy_id"
        private const val FROM_FAVOURITES_SCREEN = "from_favourites_screen"

        fun createArgs(vacancyId: Long, isFromFavouritesScreen: Boolean): Bundle =
            bundleOf(ARGS_VACANCY_ID to vacancyId, FROM_FAVOURITES_SCREEN to isFromFavouritesScreen)
    }
}
