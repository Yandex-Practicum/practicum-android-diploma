package ru.practicum.android.diploma.ui.vacancy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.first
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.presentation.vacancy.VacancyDetailsCommand
import ru.practicum.android.diploma.presentation.vacancy.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyUiState
import ru.practicum.android.diploma.ui.common.PaddingItemDecoration
import ru.practicum.android.diploma.ui.common.dp
import ru.practicum.android.diploma.ui.common.launchAndRepeatOnLifecycle
import ru.practicum.android.diploma.util.toFavoriteIcon

class VacancyDetailsFragment : Fragment() {

    private val args by navArgs<VacancyDetailsFragmentArgs>()

    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding: FragmentVacancyDetailsBinding get() = _binding!!
    private val viewModel: VacancyDetailsViewModel by viewModel { parametersOf(args.vacancyId) }

    private val adapter = VacancyDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUi()
        binding.toolbarVacancy.let {
            it.setNavigationIcon(R.drawable.arrow_back)
            it.setNavigationOnClickListener { viewModel.onBackClicked() }
        }

        binding.imFavorites.setOnClickListener {
            launchAndRepeatOnLifecycle {
                viewModel
                    .favoriteControl(
                        viewModel.uiState.first().vacancyDetails!!
                    )
            }
        }
    }

    private fun configureUi() {
        binding.imSharing.setOnClickListener { viewModel.onShareClick() }

        binding.contentList.adapter = adapter
        binding.contentList.addItemDecoration(
            PaddingItemDecoration(
                0,
                0,
                16.dp,
                16.dp
            )
        )
        launchAndRepeatOnLifecycle { viewModel.uiState.collect { handleUiState(it) } }
        launchAndRepeatOnLifecycle { viewModel.commands.collect { handleCommands(it) } }
    }

    private fun handleUiState(uiState: VacancyUiState) {
        binding.imFavorites.isClickable = !uiState.isError
        binding.imFavorites.setImageResource(uiState.isFavorite.toFavoriteIcon())
        binding.progressBar.isVisible = uiState.isFetching
        binding.contentList.isVisible = uiState.isContentVisible
        binding.vacancyNotFound.root.isVisible = uiState.isEmptyVisible
        adapter.submitList(uiState.items)
    }

    private fun handleCommands(command: VacancyDetailsCommand) {
        val navController = findNavController()
        when (command) {
            is VacancyDetailsCommand.NavigateBack -> navController.popBackStack()
            is VacancyDetailsCommand.NavigateToShare -> share(command.url)
        }
    }

    private fun share(url: String) {
        val message = url
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "*/*"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
