package ru.practicum.android.diploma.ui.vacancy

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.ui.root.BindingFragment

class VacancyFragment : BindingFragment<FragmentVacancyBinding>() {
    private val args: VacancyFragmentArgs by navArgs()
    private val viewModel by viewModel<VacancyViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVacancyBinding {
        return FragmentVacancyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadVacancyDetails(args.vacancyId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vacancyDetails.collect { vacancy ->
                    if (vacancy != null) {
                        binding.vacancy.setText(Html.fromHtml("${vacancy.title}\n${vacancy.salaryFrom}\n${vacancy.experience}\n${vacancy.employment}\n${vacancy.schedule}\n" +
                            "${vacancy.descriptionHtml}", Html.FROM_HTML_MODE_COMPACT))

                    } else {
                        binding.vacancy.text = "Нет данных"
                    }
                }
            }
        }
    }


}
