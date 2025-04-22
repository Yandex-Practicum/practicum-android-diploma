package ru.practicum.android.diploma.presentation.vacancy

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
import ru.practicum.android.diploma.domain.models.main.Salary

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
            viewModel.getLongVacancy(requireArguments().getInt("id"))
        }

        binding.bToSearch.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.bShare.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "SHARING....",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.bFavorite.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "ADDING TO DB....",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Content -> {
                val vacancy = state.vacancy

                binding.nameCompany.text = vacancy.name
                binding.salary.text = viewModel.formatSalary(vacancy.salary)
                Glide.with(binding.icCompany)
                    .load(vacancy.logoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .transform(RoundedCorners(12))
                    .into(binding.icCompany)
                binding.nameCompany.text = vacancy.employer?.name
                binding.placeCompany.text = vacancy.address?.city ?: vacancy.areaName
                binding.experience.text = viewModel.getExperienceLabelById(vacancy.experience?.id)
                binding.employmentFormSchedule.text = viewModel.formatEmploymentAndSchedule(vacancy.employmentForm?.id, vacancy.schedule?.id, requireContext())
                binding.vacancyDescription.text = Html.fromHtml(vacancy.description, Html.FROM_HTML_MODE_LEGACY)
                if (vacancy.keySkills.isEmpty()) {
                    binding.headerKeySkills.visibility = View.GONE
                    binding.listKeySkills.visibility = View.GONE
                } else {
                    binding.listKeySkills.text = vacancy.keySkills.joinToString("\n") { "• $it" }
                }

                binding.emptyPlaceholder.placeholder.visibility = View.GONE
                binding.progressBar.visibility = View.GONE

                binding.toggleVacancyVisibility(true)

            }

            VacancyState.Empty -> {
                binding.toggleVacancyVisibility(false)
                binding.emptyPlaceholder.placeholder.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }

            VacancyState.Loading -> {
                binding.toggleVacancyVisibility(false)
                binding.emptyPlaceholder.placeholder.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
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

}
