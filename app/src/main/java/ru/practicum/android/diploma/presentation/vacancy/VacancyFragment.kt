package ru.practicum.android.diploma.presentation.vacancy

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.main.VacancyLong

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding: FragmentVacancyBinding
        get() = _binding ?: error("Binding is not initialized")

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vacancyState.collect { state ->
                    render(state)
                }
            }
        }

        if (arguments != null) {
            viewModel.getLongVacancy(requireArguments().getString("vacancyId") ?: "")
        }

        binding.bToSearch.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.bFavorite.setOnClickListener {
            viewModel.reactOnLikeButton()
        }
        viewModel.isLiked.observe(viewLifecycleOwner) { updateIsLiked(it) }
    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Content -> renderContent(state.vacancy)
            VacancyState.Empty -> renderEmpty()
            VacancyState.Loading -> renderLoading()
        }
    }

    private fun renderContent(vacancy: VacancyLong) = with(binding) {
        toggleVacancyVisibility(true)
        renderIsLiked(vacancy.vacancyId)
        renderTextFields(vacancy)
        renderLogo(vacancy)
        renderSkills(vacancy)
        renderShare(vacancy)
        emptyPlaceholder.placeholder.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun renderTextFields(vacancy: VacancyLong) = with(binding) {
        vacancyName.text = vacancy.name
        salary.text = viewModel.formatSalary(vacancy.salary)
        nameCompany.text = vacancy.employer?.name
        placeCompany.text = vacancy.address?.city ?: vacancy.areaName
        experience.text = viewModel.getExperienceLabelById(vacancy.experience?.id)
        employmentFormSchedule.text = viewModel.formatEmploymentAndSchedule(
            vacancy.employmentForm?.id,
            vacancy.schedule?.id,
            requireContext()
        )
        vacancyDescription.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_LEGACY)
    }

    private fun renderLogo(vacancy: VacancyLong) = with(binding.icCompany) {
        val radius = resources.getDimensionPixelSize(R.dimen.radius_12)
        Glide.with(this)
            .load(vacancy.logoUrl?.logo240)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .transform(RoundedCorners(radius))
            .into(this)
    }

    private fun renderSkills(vacancy: VacancyLong) = with(binding) {
        if (vacancy.keySkills.isEmpty()) {
            headerKeySkills.visibility = View.GONE
            listKeySkills.visibility = View.GONE
        } else {
            headerKeySkills.visibility = View.VISIBLE
            listKeySkills.visibility = View.VISIBLE
            listKeySkills.text = vacancy.keySkills.joinToString("\n") { "• $it" }
        }
    }

    private fun renderShare(vacancy: VacancyLong) = with(binding.bShare) {
        setOnClickListener {
            val context = requireContext()
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = context.getString(R.string.intent_sharing_type)
                putExtra(Intent.EXTRA_TEXT, vacancy.url)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(shareIntent)
        }
    }

    private fun renderEmpty() = with(binding) {
        toggleVacancyVisibility(false)
        emptyPlaceholder.placeholder.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun renderLoading() = with(binding) {
        toggleVacancyVisibility(false)
        emptyPlaceholder.placeholder.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun renderIsLiked(vacancyId: String){
        lifecycleScope.launch {
            viewModel.checkInLiked(vacancyId)
        }
    }

    private fun FragmentVacancyBinding.toggleVacancyVisibility(show: Boolean) {
        val views = listOf(
            vacancyName,
            salary,
            boxCompany,
            headerExperience,
            experience,
            employmentFormSchedule,
            headerDescription,
            vacancyDescription,
            headerKeySkills,
            listKeySkills
        )

        views.forEach { it.visibility = if (show) View.VISIBLE else View.GONE }
    }

    private fun updateIsLiked(isLiked: Boolean){
        binding.bFavorite.setImageResource(
            if (isLiked) R.drawable.ic_favorites_on else R.drawable.ic_favorites_off
        )
    }
}
