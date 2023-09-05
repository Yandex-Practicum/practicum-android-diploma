package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceFilterBinding
import ru.practicum.android.diploma.filter.ui.view_models.WorkPlaceViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject


class WorkPlaceFilterFragment : Fragment(R.layout.fragment_work_place_filter) {

    private val binding by viewBinding<FragmentWorkPlaceFilterBinding>()
    private val viewModel: WorkPlaceViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val args by navArgs<WorkPlaceFilterFragmentArgs>()
    @Inject lateinit var debouncer: Debouncer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderScreen()
        initListeners()
    }

    private fun renderScreen() {
        if (args.country != null) {
            viewModel.log(thisName, "renderScreen() args.country != null")
            with(binding) {
                countyHint.visibility = View.VISIBLE
                countryText.text = args.country?.name
                countryItem.setImageResource(R.drawable.close_btn)
                countryText.setTextColor(requireActivity().getColor(R.color.black))
            }
        }
        if (args.region != null) {
            viewModel.log(thisName, "renderScreen() args.region != null")
            with(binding) {
                countyHint.visibility = View.VISIBLE
                countryText.text = args.country?.name
                countryItem.setImageResource(R.drawable.close_btn)
                countryText.setTextColor(requireActivity().getColor(R.color.black))
            }
        }
        if (args.region != null || args.country != null) {
            viewModel.log(thisName, "renderScreen() btn.VISIBLE")
            binding.chooseBtn.visibility = View.VISIBLE
        }
    }



    private fun initListeners() {
        with(binding) {
            filterToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            countryContainer.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToCountryFilterFragment()
                )
            }
            regionContainer.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToRegionFilterFragment()
                )
            }
            chooseBtn.debounceClickListener(debouncer) {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToFilterBaseFragment(
                        args.country,
                        args.region
                    )
                )
            }
        }
    }
}