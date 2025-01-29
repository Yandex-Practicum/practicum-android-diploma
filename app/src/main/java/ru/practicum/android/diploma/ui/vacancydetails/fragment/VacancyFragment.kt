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
import java.util.Locale
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

        binding.imageViewFavorites.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        binding.imageViewSharing.setOnClickListener {
            viewModel.shareVacancy()
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

        val paddingLeft = dpToPx(NUMBER_FOR_DP_TO_PX, requireContext())

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

        if (vacancy.employer?.logoUrls?.size240 != null) {
            binding.imageViewVacancyLogo.strokeWidth = 0F
            Glide.with(this@VacancyFragment)
                .load(vacancy.employer.logoUrls.size240)
                .centerCrop()
                .into(binding.imageViewVacancyLogo)
        }
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
            else -> currency
        }
        return when {
            from != null && to == null -> "от ${formattedSalary(from)} $currencyInSymbol"
            from == null && to != null -> "до ${formattedSalary(to)} $currencyInSymbol"
            from != null && to != null -> "от ${formattedSalary(from)} до ${formattedSalary(to)} $currencyInSymbol"
            else -> requireContext().getString(R.string.salary_not_specified)
        }
    }

    private fun formattedSalary(value: Int): String {
        return "%,d".format(value).replace(",", " ")
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    private fun htmlContentForDescription(stringFromApi: String?, paddingLeft: Int): String {
        val typedArray = requireContext().theme.obtainStyledAttributes(
            intArrayOf(R.attr.blackToWhite, R.attr.whiteToBlack) // Цвет текста и фона
        )
        val textColor = typedArray.getColor(0, 0xFFFFFFFF.toInt()) // Цвет текста (белый/чёрный)
        val backgroundColor = typedArray.getColor(1, 0xFF000000.toInt()) // Цвет фона (чёрный/белый)
        typedArray.recycle()
        val textColorHex = String.format(Locale.US, "#%06X", MASK_FOR_COLOR and textColor)
        val backgroundColorHex = String.format(Locale.US, "#%06X", MASK_FOR_COLOR and backgroundColor)
        val htmlContent = htmlStyle(textColorHex, backgroundColorHex, paddingLeft, stringFromApi)

        return htmlContent
    }

    private fun htmlStyle(
        textColorHex: String,
        backgroundColorHex: String,
        paddingLeft: Int,
        stringFromApi: String?
    ): String {
        return HtmlTemplate.format(textColorHex, backgroundColorHex, paddingLeft, stringFromApi)
    }

    companion object {
        private const val ARGS_VACANCY_ID = "vacancy_id"
        private const val FROM_FAVORITES_SCREEN = "from_favorites_screen"
        private const val NUMBER_FOR_DP_TO_PX = 10f
        private const val MASK_FOR_COLOR = 0xFFFFFF

        fun createArgs(vacancyId: Long, isFromFavoritesScreen: Boolean): Bundle =
            bundleOf(ARGS_VACANCY_ID to vacancyId, FROM_FAVORITES_SCREEN to isFromFavoritesScreen)
    }
}

const val HtmlTemplate = """
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
                color: %s; /* Динамический цвет текста */
                background-color: %s; /* Динамический цвет фона */
            }
            ul, ol {
                padding-left: %dpx; /* Добавляем отступ слева для списков */
            }
            ul li::marker {
                font-size: 9px; /* Уменьшаем размер точек */
            }
        </style>
    </head>
    <body>
        %s
    </body>
    </html>
"""
