package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyDetailsCompanyBinding
import ru.practicum.android.diploma.databinding.VacancyDetailsDescriptionBinding
import ru.practicum.android.diploma.databinding.VacancyDetailsExperienceBinding
import ru.practicum.android.diploma.databinding.VacancyDetailsKeySlillsBinding
import ru.practicum.android.diploma.databinding.VacancyDetailsNameItemBinding
import ru.practicum.android.diploma.databinding.VacancyDeteilsKeySkillsItemBinding
import ru.practicum.android.diploma.presentation.vacancy.VacancyDetailsItemUiModel
import ru.practicum.android.diploma.ui.common.UiModelDiffCallback

class VacancyDetailsAdapter : ListAdapter<VacancyDetailsItemUiModel,
    RecyclerView.ViewHolder>(UiModelDiffCallback<VacancyDetailsItemUiModel>()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is VacancyDetailsItemUiModel.VacancyName -> R.layout.vacancy_details_name_item
            is VacancyDetailsItemUiModel.VacancyCompany -> R.layout.vacancy_details_company
            is VacancyDetailsItemUiModel.VacancyExperience -> R.layout.vacancy_details_experience
            is VacancyDetailsItemUiModel.VacancyDescription -> R.layout.vacancy_details_description
            is VacancyDetailsItemUiModel.VacancyKeySkills -> R.layout.vacancy_details_key_slills
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.vacancy_details_name_item -> {
                VacancyDetailsNameItemViewHolder(
                    VacancyDetailsNameItemBinding.inflate(inflater, parent, false)
                )
            }

            R.layout.vacancy_details_company -> {
                VacancyDetailsCompanyViewHolder(
                    VacancyDetailsCompanyBinding.inflate(inflater, parent, false)
                )
            }

            R.layout.vacancy_details_experience -> {
                VacancyDetailsExperienceViewHolder(
                    VacancyDetailsExperienceBinding.inflate(inflater, parent, false)
                )
            }

            R.layout.vacancy_details_description -> {
                VacancyDetailsDescriptionViewHolder(
                    VacancyDetailsDescriptionBinding.inflate(inflater, parent, false)
                )
            }

            R.layout.vacancy_details_key_slills -> {
                VacancyDetailsKeySkillsViewHolder(
                    VacancyDetailsKeySlillsBinding.inflate(inflater, parent, false)
                )
            }

            else -> throw IllegalArgumentException("Неожиданный viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is VacancyDetailsItemUiModel.VacancyName -> {
                (holder as VacancyDetailsNameItemViewHolder).bind(item)
            }

            is VacancyDetailsItemUiModel.VacancyCompany -> {
                (holder as VacancyDetailsCompanyViewHolder).bind(item)
            }

            is VacancyDetailsItemUiModel.VacancyExperience -> {
                (holder as VacancyDetailsExperienceViewHolder).bind(item)
            }

            is VacancyDetailsItemUiModel.VacancyDescription -> {
                (holder as VacancyDetailsDescriptionViewHolder).bind(item)
            }

            is VacancyDetailsItemUiModel.VacancyKeySkills -> {
                (holder as VacancyDetailsKeySkillsViewHolder).bind(item)
            }
        }
    }

    inner class VacancyDetailsNameItemViewHolder(val binding: VacancyDetailsNameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: VacancyDetailsItemUiModel.VacancyName) {
            with(binding) {
                nameVacancyTv.text = item.name
                val salaryText = getSalaryText(item, context)
                salaryTv.text = salaryText
            }
        }
    }

    inner class VacancyDetailsCompanyViewHolder(val binding: VacancyDetailsCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: VacancyDetailsItemUiModel.VacancyCompany) {
            with(binding) {
                Glide
                    .with(context)
                    .load(item.logoUrl)
                    .fitCenter()
                    .placeholder(R.drawable.employer_logo_placeholder)
                    .into(logoCard)
                nameTv.text = item.name
                regionTv.text = item.region
            }
        }
    }

    inner class VacancyDetailsExperienceViewHolder(val binding: VacancyDetailsExperienceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VacancyDetailsItemUiModel.VacancyExperience) {
            with(binding) {
                experienceTv.text = item.experience
                scheduleTv.text = item.schedule
            }
        }
    }

    inner class VacancyDetailsDescriptionViewHolder(val binding: VacancyDetailsDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VacancyDetailsItemUiModel.VacancyDescription) {
            with(binding) {
                descriptionTv.text = Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    inner class VacancyDetailsKeySkillsViewHolder(val binding: VacancyDetailsKeySlillsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: VacancyDetailsItemUiModel.VacancyKeySkills) {
            with(binding) {
                val inflater = LayoutInflater.from(context)
                item.keySkills.forEach { skill ->
                    val skillItem = VacancyDeteilsKeySkillsItemBinding.inflate(
                        inflater,
                        binding.keySkillsLayout,
                        false
                    )
                    skillItem.keySkillsTv.text = skill
                    keySkillsLayout.addView(skillItem.root)
                }
            }
        }
    }

    private fun getSalaryText(item: VacancyDetailsItemUiModel.VacancyName, context: Context): String {
        return StringBuilder().apply {
            if (item.from != null || item.to != null) {
                if (item.from != null) {
                    append(context.getString(R.string.from))
                    append(" ")
                    append(item.from)
                }
                if (item.to != null) {
                    append(" ")
                    append(context.getString(R.string.to))
                    append(" ")
                    append(item.to)
                }
                append(" ")
                append(item.currency)
            } else {
                append(context.getString(R.string.salary_not_specified))
            }
        }.toString()
    }
}
