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
        initAddToFavorite()
    }

    private fun initAddToFavorite(){
        binding.lottieHeart.setOnClickListener {heartButton ->
                    viewModel.handleAddToFavsButton(args.vacancy)
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
