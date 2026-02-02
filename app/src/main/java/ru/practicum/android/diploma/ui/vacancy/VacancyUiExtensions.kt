package ru.practicum.android.diploma.ui.vacancy


import androidx.core.view.isVisible
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.VacancyDetailsError
import ru.practicum.android.diploma.R

fun FragmentVacancyBinding.showLoading() {
    name.isVisible = false
    salary.isVisible = false
    backgroundTitle.isVisible = false
    companyImage.isVisible = false
    industryName.isVisible = false
    areaName.isVisible = false
    placeholderNotFoundVacancy.isVisible = false
    textImageCaptionNotFoundVacancy.isVisible = false
    scrollView.isVisible = false
    progressBarVacancy.isVisible = true
}

fun FragmentVacancyBinding.showError(error: VacancyDetailsError) {
    when (error) {
        is VacancyDetailsError.Network -> {
            placeholderNotFoundVacancy.setImageResource(R.drawable.img_no_internet)
            textImageCaptionNotFoundVacancy.text =
                root.context.getString(R.string.no_internet)
        }

        is VacancyDetailsError.Server -> {
            placeholderNotFoundVacancy.setImageResource(R.drawable.img_vacancy_search_error)
            textImageCaptionNotFoundVacancy.text =
                root.context.getString(R.string.server_error)
        }

        is VacancyDetailsError.NotFound -> {
            placeholderNotFoundVacancy.setImageResource(R.drawable.img_vacancy_not_found)
            textImageCaptionNotFoundVacancy.text =
                root.context.getString(R.string.vacancy_not_found)
        }
    }

    name.isVisible = false
    salary.isVisible = false
    backgroundTitle.isVisible = false
    companyImage.isVisible = false
    industryName.isVisible = false
    areaName.isVisible = false
    placeholderNotFoundVacancy.isVisible = true
    textImageCaptionNotFoundVacancy.isVisible = true
    scrollView.isVisible = false
    progressBarVacancy.isVisible = false
}

fun FragmentVacancyBinding.showContent(hasSkills: Boolean) {
    name.isVisible = true
    salary.isVisible = true
    backgroundTitle.isVisible = true
    companyImage.isVisible = true
    industryName.isVisible = true
    areaName.isVisible = true
    placeholderNotFoundVacancy.isVisible = false
    textImageCaptionNotFoundVacancy.isVisible = false
    scrollView.isVisible = true
    experience.isVisible = true
    experienceDescription.isVisible = true
    schedule.isVisible = true
    vacancyDescriptionTitle.isVisible = true
    duties.isVisible = true
    dutyList.isVisible = false
    requirements.isVisible = true
    requirementsList.isVisible = false
    conditions.isVisible = false
    conditionsList.isVisible = false
    progressBarVacancy.isVisible = false
    skillsTitle.isVisible = hasSkills
    skillsText.isVisible = hasSkills
}
