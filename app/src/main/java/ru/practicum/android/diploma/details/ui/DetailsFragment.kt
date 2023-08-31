package ru.practicum.android.diploma.details.ui

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.VacancyFullInfoModel
import ru.practicum.android.diploma.util.setImage
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding<FragmentDetailsBinding>()
    private val viewModel: DetailsViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val args by navArgs<DetailsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.log(thisName, "onViewCreated()")
        drawMainInfo()
        viewModel.getVacancyByID(args.vacancy.id)
        collector()
        pressSimilarVacanciesButton()
    }

    private fun collector() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is DetailsScreenState.Empty -> Unit
                    is DetailsScreenState.Content -> {
                        showDescription(state.result)
                    }
                }
            }
        }
    }

    private fun showDescription(vacancy: VacancyFullInfoModel) {
        with(binding) {
            viewModel.log(thisName, "showDescription()")
            val tvSchedule = vacancy.employment?.name + ". " + vacancy.schedule?.name
            val formattedDescription =
                HtmlCompat.fromHtml(vacancy.description!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
            val tvKeySkillsList =
                vacancy.keySkills?.mapIndexed { _, skill -> "• ${skill.name}" }?.joinToString("\n")
            val phoneList = vacancy.contacts?.phones?.mapIndexed { _, phone ->
                "+" + phone.country +
                        " (${phone.city}) " + phone.number
            }?.joinToString("\n")

            tvExperience.text = vacancy.experience?.name
            tvScheduleEmployment.text = tvSchedule
            tvDescription.text = formattedDescription
            tvKeySkills.text = tvKeySkillsList
            tvContactsName.text = vacancy.contacts?.name
            tvContactsEmail.text = vacancy.contacts?.email
            tvContactsPhone.text = phoneList
        }
    }


    private fun pressSimilarVacanciesButton() {
        binding.buttonSameVacancy.setOnClickListener {
            viewModel.log(thisName, "buttonSameVacancy.setOnClickListener { }")
            navigateToSimilarVacancies()
        }
    }

    private fun navigateToSimilarVacancies() {
        findNavController().navigate(
            DetailsFragmentDirections.actionDetailsFragmentToSimilarsVacancyFragment(args.vacancy)
        )
    }

    private fun drawMainInfo() {
        with(binding) {
            viewModel.log(thisName, "drawMainInfo()")
            with(args.vacancy){
                tvNameOfVacancy.text = title
                tvSalary.text = salary
                tvNameOfCompany.text = company
                tvArea.text = area
                imageView.setImage(iconUri, R.drawable.ic_placeholder_company)
            }
        }
    }
}
