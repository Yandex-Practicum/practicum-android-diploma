package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.SalaryFormatter
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.vmodels.VacancyDetailViewModel
import ru.practicum.android.diploma.ui.root.search.VacancyDetailState

class VacancyDetailFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vacancyId = arguments?.getString("vacancyId").toString()
        viewModel.search(vacancyId)
        observeViewModel()
        binding.returnButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.vacancyDetailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyDetailState.Success -> getVacancyDetail(state.vacancyDetail)
            }
        }
    }

    private fun getVacancyDetail(vacancyDetail: VacancyDetail) {
        binding.vacancyTitle.text = vacancyDetail.name
        val salaryText = vacancyDetail.salary?.let { salary ->
            SalaryFormatter.getFormattedSalary(salary)
        } ?: "Зарплата не указана"
        binding.salaryText.text = salaryText

        Glide.with(this)
            .load(vacancyDetail.employer?.logoUrl)
            .placeholder(R.drawable.ic_company_placeholder)
            .error(R.drawable.ic_company_placeholder)
            .into(binding.vacancyCover)

        binding.industry.text = vacancyDetail.industry?.name
        binding.area.text = vacancyDetail.area?.name
        binding.experience.text = vacancyDetail.experience?.name
        binding.schedule.text = vacancyDetail.schedule?.name
        binding.employment.text = vacancyDetail.employment?.name
        binding.description.text = vacancyDetail.description
    }
}
