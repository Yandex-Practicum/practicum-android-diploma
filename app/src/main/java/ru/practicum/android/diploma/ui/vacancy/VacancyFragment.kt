package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.vacancy.model.VacancyState
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyViewModel>()

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
        viewModel.getVacancyScreenStateLiveData.observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getVacancyRessurces("114171883")

    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Loading -> showLoading()
            is VacancyState.Error -> showError()
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
        Toast(context).show()
    }

    private fun showEmpty() {
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
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
            "RUR" to "₽",
            "BYR" to "Br",
            "USD" to "$",
            "EUR" to "€",
            "RUB" to "₽",
            "BYN" to "Br"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
