package ru.practicum.android.diploma.ui.vacancy.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
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
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancy.VacancyState
import ru.practicum.android.diploma.ui.vacancy.viewmodel.FavoriteVacancyButtonState
import ru.practicum.android.diploma.ui.vacancy.viewmodel.VacancyViewModel
import ru.practicum.android.diploma.util.DimenConvertor
import java.util.Locale

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
                viewModel.deleteVacancyFromFavorites()
            } else {
                viewModel.insertVacancyInFavorites()
            }
        }

        binding.ivBack.setOnClickListener {
            goToPrevScreen()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            goToPrevScreen()
        }

        if (arguments != null) {
            val vacancyId = requireArguments().getString(KEY_FOR_BUNDLE_DATA)
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
        }
    }

    private fun showLoading() {
        changeClickableOfButtons(false)
        binding.progressBarVacancy.isVisible = true
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = false
    }

    private fun showError() {
        changeClickableOfButtons(false)
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = true
        binding.atvErrorInternet.isVisible = false
        binding.atvVacancyNotFound.isVisible = false
    }

    private fun badRequest() {
        changeClickableOfButtons(false)
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = true
        binding.atvErrorInternet.isVisible = false
        binding.atvVacancyNotFound.isVisible = false
    }

    private fun networkError() {
        changeClickableOfButtons(false)
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = false
        binding.atvErrorInternet.isVisible = true
        binding.atvVacancyNotFound.isVisible = false
    }

    private fun showEmpty() {
        changeClickableOfButtons(false)
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = false
        binding.llVacancyNotFound.isVisible = true
        binding.atvErrorServer.isVisible = false
        binding.atvErrorInternet.isVisible = false
        binding.atvVacancyNotFound.isVisible = true
    }

    private fun showContent(item: Vacancy) {
        val imgSizeInPx =
            DimenConvertor.dpToPx(requireContext().resources.getDimension(R.dimen.img_size48), requireContext())
        binding.progressBarVacancy.isVisible = false
        binding.scrollableContent.isVisible = true
        binding.llVacancyNotFound.isVisible = false
        binding.tvName.text = item.titleOfVacancy
        binding.tvSalary.text = item.salary
        Glide.with(this)
            .load(item.employerLogoUrl)
            .override(imgSizeInPx, imgSizeInPx)
            .transform(RoundedCorners(DimenConvertor.dpToPx(RADIUS_OF_ROUNDED_CORNERS_IN_DP, requireContext())))
            .placeholder(R.drawable.grey_android_icon)
            .into(binding.ivImageEmployer)
        binding.tvEmployer.text = item.employerName
        binding.tvAddressEmployer.text = item.regionName
        binding.tvExperienceText.text = item.experience
        binding.tvScheduleText.text =
            String.format(Locale.getDefault(), "%s, %s", item.employmentType, item.scheduleType)
        binding.tvResponsibilitiesText.text = Html.fromHtml(item.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvKeySkillsTitle.isVisible = item.keySkills != null
        binding.tvKeySkillsText.isVisible = item.keySkills != null
        binding.tvKeySkillsText.text = item.keySkills
        changeClickableOfButtons(true)
    }

    private fun changeClickableOfButtons(clickable: Boolean) {
        binding.ivSharing.isClickable = clickable
        binding.ivSharing.isActivated = clickable
        binding.ivFavorites.isClickable = clickable
        binding.ivFavorites.isActivated = clickable
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
        private const val RADIUS_OF_ROUNDED_CORNERS_IN_DP = 12f
    }
}
