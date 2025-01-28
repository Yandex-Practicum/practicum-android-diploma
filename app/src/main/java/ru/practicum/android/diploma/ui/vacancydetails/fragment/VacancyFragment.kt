package ru.practicum.android.diploma.ui.vacancydetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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
    private var isFromFavoritesScreen: Boolean = false

    private val viewModel: VacancyViewModel by viewModel { parametersOf(vacancyId, isFromFavoritesScreen) }

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
        isFromFavoritesScreen = requireArguments().getBoolean(FROM_FAVORITES_SCREEN)
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

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
            R.drawable.ic_like_on_red_24
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
        binding.pbVacancy.isVisible = true
        binding.overlay.isVisible = false
        binding.placeholder.layoutPlaceholder.isVisible = false
        binding.scrollView.isVisible = false
    }

    private fun showServerErrorPlaceholder() {
        binding.pbVacancy.isVisible = false
        binding.overlay.isVisible = false
        binding.scrollView.isVisible = false
        binding.placeholder.imageViewPlaceholder.setImageResource(R.drawable.placeholder_vacancy_server_error)
        binding.placeholder.textViewPlaceholder.setText(R.string.server_error)
        binding.placeholder.layoutPlaceholder.isVisible = true
    }

    private fun showNotFoundPlaceholder() {
        binding.pbVacancy.isVisible = false
        binding.overlay.isVisible = false
        binding.scrollView.isVisible = false
        binding.placeholder.imageViewPlaceholder.setImageResource(R.drawable.placeholder_vacancy_deleted)
        binding.placeholder.textViewPlaceholder.setText(R.string.vacancy_not_found)
        binding.placeholder.layoutPlaceholder.isVisible = true
    }

    private fun showVacancyDetails(vacancy: Vacancy) {
        binding.pbVacancy.isVisible = false
        binding.overlay.isVisible = false
        binding.scrollView.isVisible = true
        binding.placeholder.layoutPlaceholder.isVisible = false
        bindData(vacancy)
    }

    private fun bindData(vacancy: Vacancy) {
        binding.textViewVacancyName.text = vacancy.name
        binding.textViewVacancyEmployerName.text = vacancy.employer?.name
        binding.textViewVacancyEmployerCity.text = vacancy.address?.city ?: vacancy.area?.name
        binding.textViewVacancyExperience.text = vacancy.experience?.name

        // ИСПРАВИТЬ!!!
        binding.textViewVacancySalary.text = vacancy.salary?.to.toString()

        // проверить!!!
        if (vacancy.employer?.logoUrls?.size240 != null) {
            binding.imageViewVacancyLogo.strokeWidth = 0F
            Glide.with(this@VacancyFragment)
                .load(vacancy.employer.logoUrls.size240)
                .centerCrop()
                .into(binding.imageViewVacancyLogo)
        }


    }

    // Временная заглушка + fix detekt
    companion object {
        private const val ARGS_VACANCY_ID = "vacancy_id"
        private const val FROM_FAVORITES_SCREEN = "from_favorites_screen"

        fun createArgs(vacancyId: Long, isFromFavoritesScreen: Boolean): Bundle =
            bundleOf(ARGS_VACANCY_ID to vacancyId, FROM_FAVORITES_SCREEN to isFromFavoritesScreen)
    }
}
