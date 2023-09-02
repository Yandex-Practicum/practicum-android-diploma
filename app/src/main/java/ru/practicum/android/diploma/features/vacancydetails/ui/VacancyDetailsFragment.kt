package ru.practicum.android.diploma.features.vacancydetails.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancydetailsBinding
import ru.practicum.android.diploma.features.vacancydetails.domain.models.Email
import ru.practicum.android.diploma.features.vacancydetails.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsEvent
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsState
import ru.practicum.android.diploma.features.vacancydetails.presentation.models.VacancyDetailsUiModel

class VacancyDetailsFragment : Fragment() {
    private val viewModel by viewModel<VacancyDetailsViewModel>()
    private val externalNavigator: ExternalNavigator by inject()

    private var _binding: FragmentVacancydetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var vacancy: VacancyDetailsUiModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancydetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVacancyById(getIdFromArgs())

        viewModel.screenState.observe(viewLifecycleOwner) {
            when (it) {
                is VacancyDetailsState.Content -> render(it.vacancy)
                is VacancyDetailsState.Error -> renderError()
                is VacancyDetailsState.Loading -> renderLoading()
            }
        }

        viewModel.externalNavEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is VacancyDetailsEvent.ComposeEmail -> composeEmail(action.email)
                    is VacancyDetailsEvent.ShareVacancy -> shareVacancy(action.sharingText)
                }
            }
        }

        setClickListeners()

    }

    private fun renderLoading() {
        binding.progressBar.isVisible = true
//        binding.details.isVisible = false
    }

    private fun renderError() {
        showMessage(getString(R.string.something_went_wrong))
        findNavController().navigateUp()
    }

    private fun setClickListeners() {
        binding.returnArrow.setOnClickListener { findNavController().navigateUp() }

        binding.shareButton.setOnClickListener {
            generateShareText()
        }

        binding.favButton.setOnClickListener { }

        binding.similarVacanciesButton.setOnClickListener { }


        binding.email.setOnClickListener {
            viewModel.composeEmail(vacancy.contactsEmail, vacancy.vacancyName)
        }


    }

    private fun render(model: VacancyDetailsUiModel) {
        vacancy = model
        binding.apply {
            vacancyName.text = vacancy.vacancyName
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateShareText() {
        val strings = listOf(
            vacancy.vacancyName,
            vacancy.salary,
            vacancy.employerName,
            vacancy.employerArea
        )
        viewModel.generateShareText(strings)
    }

    private fun shareVacancy(message: String) {
        if (message.isEmpty()) {
            showMessage(getString(R.string.empty_share_text))
        } else {
            val chooserMessage = getString(R.string.share_to)
            val intent = externalNavigator.getShareIntent(message, chooserMessage)
            tryStartActivity(intent)
        }
    }

    private fun composeEmail(email: Email) {
        val intent = externalNavigator.getEmailIntent(email)
        tryStartActivity(intent)
    }

    private fun tryStartActivity(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showMessage(getString(R.string.no_app_found))
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getIdFromArgs(): String {
        return requireArguments().getString(ARGS_VACANCY_ID) ?: ""
    }

    companion object {

        private const val ARGS_VACANCY_ID = "ARGS_VACANCY_ID"
        fun createArgs(id: String): Bundle =
            bundleOf(ARGS_VACANCY_ID to id)
    }
}