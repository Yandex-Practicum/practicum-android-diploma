package ru.practicum.android.diploma.ui.vacancy.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import java.text.NumberFormat
import java.util.Locale
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancy.VacancyState
import ru.practicum.android.diploma.ui.vacancy.viewmodel.FavoriteVacancyButtonState
import ru.practicum.android.diploma.ui.vacancy.viewmodel.VacancyViewModel
import ru.practicum.android.diploma.util.DimenConvertor

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val item = arguments?.getString("vacancy_id")
        item.let { itemId ->
            if (itemId != null) ID_VACANCY = itemId
        }
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVacancyScreenStateLiveData.observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getShareLinkStateLiveData.observe(viewLifecycleOwner) {
            shareVacancy(it.shareLink)
        }

        viewModel.getFavoriteVacancyButtonStateLiveData.observe(viewLifecycleOwner) { favoriteButtonState ->
            binding.ivFavorites.setImageResource(
                if (favoriteButtonState is FavoriteVacancyButtonState.VacancyIsFavorite) {
                    R.drawable.favorites_on
                } else {
                    R.drawable.favorites_off
                }
            )
        }

        binding.ivSharing.setOnClickListener {
            viewModel.shareVacancy()
        }

        binding.ivFavorites.setOnClickListener {
            if (viewModel.getFavoriteVacancyButtonStateLiveData.value == FavoriteVacancyButtonState.VacancyIsFavorite) {
                Log.d("testt", "vac is fav")
                viewModel.deleteVacancyFromFavorites()
            } else {
                Log.d("testt", "vac is not fav")
                viewModel.insertVacancyInFavorites()
            }
        }

        binding.ivBack.setOnClickListener {
            goToPrevScreen()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            goToPrevScreen()
        }

        val bundle = this.arguments
        if (bundle != null) {
            val vacancyId = bundle.getString(KEY_FOR_BUNDLE_DATA)
            viewModel.getVacancyResources(vacancyId!!)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToPrevScreen() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = true
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Loading -> showLoading()
            is VacancyState.ServerError -> showError()
            is VacancyState.BadRequest -> badRequest()
            is VacancyState.NetworkError -> networkError()
            is VacancyState.Empty -> showEmpty()
            is VacancyState.Content -> showContent(state.item)
            is VacancyState.ContentWithAppEntity -> showContentWithVacancyEntity(state.item)
        }
    }

    private fun showContentWithVacancyEntity(vacancyForDisplay: Vacancy) {
        val imgSizeInPx =
            DimenConvertor.dpToPx(requireContext().resources.getDimension(R.dimen.img_size48), requireContext())
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = true
        binding.llVacancyNotFound.isVisible = false
        binding.tvName.text = vacancyForDisplay.titleOfVacancy
        binding.tvSalary.text = vacancyForDisplay.salary
        Glide.with(this)
            .load(vacancyForDisplay.employerLogoUrl)
            .override(imgSizeInPx, imgSizeInPx)
            .transform(RoundedCorners(R.dimen.corner_radius_10))
            .placeholder(R.drawable.grey_android_icon)
            .into(binding.ivImageEmployer)
        binding.tvEmployer.text = vacancyForDisplay.employerName
        binding.tvAddressEmployer.text = vacancyForDisplay.regionName
        binding.tvExperienceText.text = vacancyForDisplay.experience
        binding.tvScheduleText.text = String.format(
            Locale.getDefault(),
            "%s, %s",
            vacancyForDisplay.employmentType,
            vacancyForDisplay.scheduleType
        )
        binding.tvResponsibilitiesText.text =
            Html.fromHtml(vacancyForDisplay.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        if (vacancyForDisplay.keySkills == null) {
            binding.tvKeySkillsText.isVisible = false
            binding.tvKeySkillsTitle.isVisible = false
        } else {
            binding.tvKeySkillsText.text = vacancyForDisplay.keySkills
            binding.tvKeySkillsText.isVisible = true
            binding.tvKeySkillsTitle.isVisible = true
        }
    }

    private fun showLoading() {
        binding.progressBarVacancy.isVisible = true
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = false
    }

    private fun showError() {
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = true
        binding.atvErrorInternet.isVisible = false
        binding.atvVacancyNotFound.isVisible = false
    }

    private fun badRequest() {
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = true
        binding.atvErrorInternet.isVisible = false
        binding.atvVacancyNotFound.isVisible = false
    }

    private fun networkError() {
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = false
        binding.atvErrorInternet.isVisible = true
        binding.atvVacancyNotFound.isVisible = false
    }

    private fun showEmpty() {
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = false
        binding.atvErrorInternet.isVisible = false
        binding.atvVacancyNotFound.isVisible = true
    }

    private fun showContent(item: VacancyFullItemDto) {
        val imgSizeInPx =
            DimenConvertor.dpToPx(requireContext().resources.getDimension(R.dimen.img_size48), requireContext())
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = true
        binding.llVacancyNotFound.isVisible = false
        binding.tvName.text = item.name
        binding.tvSalary.text = salary(item)
        Glide.with(this)
            .load(item.employer.logoUrls?.logo90pxUrl)
            .override(imgSizeInPx, imgSizeInPx)
            .transform(RoundedCorners(R.dimen.corner_radius_10))
            .placeholder(R.drawable.grey_android_icon)
            .into(binding.ivImageEmployer)
        binding.tvEmployer.text = item.employer.name
        binding.tvAddressEmployer.text = getCorrectFormOfEmployerAddress(item)
        binding.tvExperienceText.text = item.experience.name
        binding.tvScheduleText.text = String.format(
            Locale.getDefault(),
            "%s, %s",
            item.employment.name,
            item.schedule.name
        )
        binding.tvResponsibilitiesText.text = Html.fromHtml(item.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        keySkills(item)
    }

    private fun salary(item: VacancyFullItemDto): String {
        val currencyCodeMapping = mapOf(
            "AZN" to "₼",
            "BYR" to "Br",
            "EUR" to "€",
//            "GEL" to "₾",
//            "KGS" to "⃀",
            "KZT" to "₸",
            "RUR" to "₽",
            "UAH" to "₴",
            "USD" to "$",
            "UZS" to "Soʻm",
        )
        val codeSalary = currencyCodeMapping[item.salary?.currency] ?: item.salary?.currency
        val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
        return when {
            item.salary == null -> "Зарплата не указана"
            item.salary.from != null && item.salary.to != null && item.salary.from == item.salary.to -> {
                "${numberFormat.format(item.salary.from)} $codeSalary"
            }

            item.salary.from != null && item.salary.to == null -> {
                "от ${numberFormat.format(item.salary.from)} $codeSalary"
            }

            item.salary.from == null && item.salary.to != null -> {
                "до ${numberFormat.format(item.salary.to)} $codeSalary"
            }

            else -> {
                "от ${numberFormat.format(item.salary.from ?: 0)} $codeSalary " +
                    "до ${numberFormat.format(item.salary.to ?: 0)} $codeSalary"
            }
        }
    }

    private fun getCorrectFormOfEmployerAddress(item: VacancyFullItemDto): String {
        return if (item.address == null) {
            item.area.name
        } else {
            item.address.raw
        }
    }

    private fun keySkills(item: VacancyFullItemDto) {
        if (item.keySkills.isEmpty()) {
            binding.tvKeySkillsText.isVisible = false
            binding.tvKeySkillsTitle.isVisible = false
        } else {
            val formatedText = item.keySkills.joinToString(separator = "\n") { itemKey ->
                "• ${itemKey.name.replace(",", ",\n")}"
            }
            binding.tvKeySkillsText.text = formatedText
            binding.tvKeySkillsText.isVisible = true
            binding.tvKeySkillsTitle.isVisible = true
        }
    }

    private fun shareVacancy(shareLink: String?) {
        if (shareLink != null) {
            val share = Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareLink)
                    setType("text/plain")
                },
                null
            )
            startActivity(share)
        }
    }

    companion object {
        private const val KEY_FOR_BUNDLE_DATA = "selected_vacancy_id"
        private var ID_VACANCY = ""

        fun newInstance(vacancyId: String): VacancyFragment {
            val fragment = VacancyFragment()
            val bundle = Bundle().apply {
                putString(KEY_FOR_BUNDLE_DATA, vacancyId)
            }
            fragment.arguments = bundle

            return fragment
        }

    }

}
