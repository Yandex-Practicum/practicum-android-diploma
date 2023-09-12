package ru.practicum.android.diploma.details.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding<FragmentDetailsBinding>()

    @Inject
    lateinit var factory: DetailsViewModel.Factory

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.provideDetailsViewModelFactory(factory, args.id)
    }

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getVacancyByID()
        collector()
        showIfInFavourite()
        pressSimilarVacanciesButton()
        initListeners()
    }

    private fun initListeners() {
        addOrDeleteFromFavorites()
        navigateUp()
        sendVacancy()
        writeEmail()
        makeCall()
    }

    private fun showIfInFavourite() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.log(thisName, "showIfInFavourite()")
            viewModel.showIfInFavouriteById()
            delay(TIME_TO_DRAW)
        }

    }

    private fun collector() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { screenState ->
                viewModel.log(thisName, "uiState.collect { state -> ${screenState.thisName}")
                val painter = DetailsScreenPainter(binding)
                when (screenState) {
                    is DetailsScreenState.Content -> painter.renderDataContent(screenState.vacancy)
                    is DetailsScreenState.AddAnimation -> painter.renderAddAnimation(viewLifecycleOwner.lifecycleScope)
                    is DetailsScreenState.DeleteAnimation -> painter
                        .renderDeleteAnimation(viewLifecycleOwner.lifecycleScope)
                    is DetailsScreenState.Offline -> painter.renderOffline(screenState.message)
                    is DetailsScreenState.Error -> painter.renderError(screenState.message)
                    is DetailsScreenState.Loading -> painter.renderLoading()
                    is DetailsScreenState.Empty -> Unit
                }
            }
        }
    }

    private fun navigateUp() {
        binding.vacancyToolbar.setNavigationOnClickListener {
            viewModel.log(thisName, "buttonNavigateUp.setOnClickListener { }")
            findNavController().navigateUp()
        }
    }

    private fun addOrDeleteFromFavorites() {
        binding.lottieHeart.setOnClickListener {
            viewModel.log(thisName, "buttonAddToFavorites.setOnClickListener { }")
            viewModel.handleAddToFavsButton()
        }
    }


    private fun sendVacancy() {
        binding.shareButton.setOnClickListener {
            viewModel.log(thisName, "buttonSendVacancy.setOnClickListener { }")
            viewModel.sendVacancy()
        }
    }

    private fun writeEmail() {
        binding.tvContactsEmail.setOnClickListener {
            viewModel.log(thisName, "buttonWriteEmail.setOnClickListener { }")
            viewModel.writeEmail(requireContext())
        }
    }

    private fun makeCall() {
        binding.tvContactsPhone.setOnClickListener {
            viewModel.log(thisName, "buttonMakeCall.setOnClickListener { }")
            viewModel.makeCall()
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
            DetailsFragmentDirections.actionDetailsFragmentToSimilarsVacancyFragment(args.id)
        )
    }

    companion object {
        private const val TIME_TO_DRAW = 500L
    }
}
