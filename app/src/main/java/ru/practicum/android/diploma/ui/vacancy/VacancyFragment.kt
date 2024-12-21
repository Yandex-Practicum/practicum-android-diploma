package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.model.favorites.ShareData
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyFragment : Fragment() {

    companion object {
        private var ID_VACANCY = ""
    }

    private var _binding: FragmentVacancyBinding? = null
    private val viewModel by viewModel<VacancyViewModel>()

    private val binding get() = _binding!!

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

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Loading -> showLoading()
            is VacancyState.ServerError -> showError()
            is VacancyState.BadRequest -> badRequest()
            is VacancyState.NetworkError -> networkError()
            is VacancyState.Empty -> showEmpty()
            is VacancyState.Content -> showContent(state.item)
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
        val imgSizeInPx = dpToPx(requireContext().resources.getDimension(R.dimen.img_size48), requireContext())
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
        binding.tvAddressEmployer.text = addresEmployer(item)
        binding.tvExperienceText.text = item.experience.name
        binding.tvScheduleText.text = "${item.employment.name}, ${item.schedule.name}"
        binding.tvResponsibilitiesText.text = Html.fromHtml(item.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        keySkills(item)
    }

    private fun salary(item: VacancyFullItemDto): String {
        val currencyCodeMapping = mapOf(
            "AZN" to "₼",
            "BYR" to "Br",
            "EUR" to "€",
            "GEL" to "₾",
            "KGS" to "⃀",
            "KZT" to "₸",
            "RUR" to "₽",
            "UAH" to "₴",
            "USD" to "$",
            "UZS" to "Soʻm",
        )
        val codeSalary = currencyCodeMapping[item.salary?.currency] ?: item.salary?.currency
        val str: String
        if (item.salary == null) {
            str = "зарплата не указана"
        } else if (item.salary.to == null || item.salary.from == item.salary.to) {
            str = "от ${item.salary.from} $codeSalary"
        } else {
            str = "от ${item.salary.from} $codeSalary до ${item.salary.to} $codeSalary"
        }
        return str
    }

    private fun addresEmployer(item: VacancyFullItemDto): String {
        val str: String
        if (item.address == null) {
            str = item.area.name
        } else {
            str = item.address.raw
        }
        return str
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

    fun dpToPx(dp: Float, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeShareState().observe(viewLifecycleOwner) { sData ->
            sData?.let { shareVacancy(it) }
        }

        viewModel.getVacancyRessurces(ID_VACANCY)
        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.ivSharing.setOnClickListener {
            viewModel.shareVacancy(ID_VACANCY)
        }

        viewModel.isVacancyInFavorites(ID_VACANCY)

        viewModel.isFavorite.observe(viewLifecycleOwner) { state ->
            updateFavoriteState(state)
        }

        binding.ivFavorites.setOnClickListener {
            viewModel.onFavoriteClicked(ID_VACANCY)
        }

        viewModel.getVacancyScreenStateLiveData.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun shareVacancy(data: ShareData) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, data.url)
            setType("text/plain")
        }, null)
        startActivity(share)
    }

    private fun updateFavoriteState(state: Boolean) {
        if (state) {
            binding.ivFavorites.setImageResource(R.drawable.favorites_on)
        } else {
            binding.ivFavorites.setImageResource(R.drawable.favorites_off)
        }
    }
}
