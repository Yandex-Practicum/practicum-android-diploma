package ru.practicum.android.diploma.vacancy.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.vacancy.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.presentation.ui.state.VacancyInputState
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyDetailViewModel

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val vacancyDetailViewModel: VacancyDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alternateUrl = ""
        binding.vacancyHeader.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.shareButton.setOnClickListener {
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, alternateUrl)
                type = "text/plain"
                Intent.createChooser(this, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(this)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val VACANCY_ID_NETWORK = "vacancy_instance"
        private const val VACANCY_ID_DB = "vacancy_id"

        private const val ARGS_STATE = "args_state"

        private const val INPUT_NETWORK_STATE = 0
        private const val INPUT_DB_STATE = 1

        fun createArgs(vacancyInputState: VacancyInputState): Bundle {
            return when (vacancyInputState) {
                is VacancyInputState.VacancyNetwork -> {
                    bundleOf(
                        ARGS_STATE to INPUT_NETWORK_STATE,
                        VACANCY_ID_NETWORK to vacancyInputState.id
                    )
                }
                is VacancyInputState.VacancyDb -> {
                    bundleOf(
                        ARGS_STATE to INPUT_DB_STATE,
                        VACANCY_ID_DB to vacancyInputState.id
                    )
                }
            }
        }
    }
}
