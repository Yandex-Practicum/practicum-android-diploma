package ru.practicum.android.diploma.ui.vacancydetails.fragment

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
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
        binding.textViewVacancyExperience.text =
            vacancy.experience?.name ?: requireContext().getString(R.string.experience_not_specified)
        binding.textViewVacancySalary.text =
            createSalaryInterval(vacancy.salary?.from, vacancy.salary?.to, vacancy.salary?.currency)
        binding.textViewVacancySchedule.text =
            vacancy.schedule?.name ?: requireContext().getString(R.string.schedule_not_specified)

        val stringFromApi = vacancy.description

        val paddingLeft = dpToPx(10f, requireContext())

        val htmlContent = htmlContentForDescription(stringFromApi, paddingLeft)
        binding.textViewVacancyDescription.loadDataWithBaseURL(
            null,
            htmlContent,
            "text/html",
            "UTF-8",
            null
        )

        if (vacancy.keySkills.isEmpty()) {
            binding.textViewVacancySkillsTitle.isVisible = false
            binding.textViewVacancySkills.isVisible = false
        } else {
            var tempString = ""
            for (skill in vacancy.keySkills) {
                tempString +=
                    requireContext().getString(R.string.dot_with_key_skill, skill?.name)
            }
            binding.textViewVacancySkills.text = tempString
        }
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

    private fun createSalaryInterval(from: Int?, to: Int?, currency: String?): String {
        val currencyInSymbol = when (currency) {
            "AZN" -> requireContext().getString(R.string.AZN)
            "BYR" -> requireContext().getString(R.string.BYR)
            "EUR" -> requireContext().getString(R.string.EUR)
            "GEL" -> requireContext().getString(R.string.GEL)
            "KGS" -> requireContext().getString(R.string.KGS)
            "KZT" -> requireContext().getString(R.string.KZT)
            "RUR" -> requireContext().getString(R.string.RUR)
            "UAH" -> requireContext().getString(R.string.UAH)
            "USD" -> requireContext().getString(R.string.USD)
            "UZS" -> requireContext().getString(R.string.UZS)
            else -> ""
        }
        return when {
            from != null && to == null -> "от $from $currencyInSymbol"
            from == null && to != null -> "до $to $currencyInSymbol"
            from != null && to != null -> "от $from до $to $currencyInSymbol"
            else -> requireContext().getString(R.string.salary_not_specified)
        }
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun htmlContentForDescription(stringFromApi: String?, paddingLeft: Int): String {
        val htmlContent = """
            <html>
            <head>
                <style>
                 @font-face {
                        font-family: 'MyCustomFont';
                        src: url('file:///android_asset/fonts/ys_display_regular.ttf');
                    }
                    body {
                        font-family: 'MyCustomFont', sans-serif;
                        font-size: 16px;
                        margin: 0; /* Убираем отступы */
                        padding: 0; /* Убираем паддинги */
                        text-align: left; /* Выравнивание текста по левому краю */
                    }
                    
                    ul, ol {
                        padding-left: ${paddingLeft}px; /* Добавляем отступ слева для списков */
                    }
                    ul li::marker {
                        font-size: 9px; /* Уменьшаем размер точек */
                    }
                </style>
            </head>
            <body>
                $stringFromApi
            </body>
            </html>
        """
        return htmlContent
    }
}
