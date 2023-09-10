package ru.practicum.android.diploma.details.ui

import android.os.Bundle
import android.view.View
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
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding<FragmentDetailsBinding>()
    private val viewModel: DetailsViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val args by navArgs<DetailsFragmentArgs>()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated()")
        viewModel.getVacancyByID(args.vacancy.id)
        collector()
        showIfInFavourite()
  //      viewModel.getFavoriteVacancyById(args.vacancy.id)
        pressSimilarVacanciesButton()
        initListeners()
    }

    private fun initListeners() {
        addToFavorites()
        navigateUp()
        sendVacancy()
        writeEmail()
        makeCall()
    }

    private fun showIfInFavourite() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getFavoriteVacancyById(args.vacancy.id)
        }
    }

    private fun collector() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                state.render(binding)
            }
        }
    }

    private fun navigateUp() {
        binding.vacancyToolbar.setNavigationOnClickListener {
            viewModel.log(thisName, "buttonNavigateUp.setOnClickListener { }")
            findNavController().navigateUp()
        }
    }

    private fun addToFavorites() {
        binding.lottieHeart.setOnClickListener {
            viewModel.log(thisName, "buttonAddToFavorites.setOnClickListener { }")
            viewModel.handleAddToFavsButton(args.vacancy.id)
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
            DetailsFragmentDirections.actionDetailsFragmentToSimilarsVacancyFragment(args.vacancy)
        )
    }
}
