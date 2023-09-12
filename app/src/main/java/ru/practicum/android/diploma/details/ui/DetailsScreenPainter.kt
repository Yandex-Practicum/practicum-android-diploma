package ru.practicum.android.diploma.details.ui

import android.view.View
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.util.setImage

class DetailsScreenPainter(private val binding: FragmentDetailsBinding) {

    fun renderDataContent(vacancy: VacancyFullInfo) {
        with(binding) {
            scrollView.visibility = View.VISIBLE
            placeHolder.visibility = View.GONE
            iwAnim.visibility = View.GONE
            hideContactsIfEmpty(vacancy)
            showKeySkills(vacancy)
            val tvSchedule = vacancy.employment + ". " + vacancy.schedule
            val formattedDescription =
                HtmlCompat.fromHtml(vacancy.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

            if (vacancy.logo.isNotEmpty()) imageView.imageTintList = null

            tvContactsPhone.text = vacancy.contactPhones.joinToString("\n")
            tvContactsComment.text = vacancy.contactComment
            tvContactsName.text = vacancy.contactName
            tvContactsEmail.text = vacancy.contactEmail
            tvExperience.text = vacancy.experience
            tvScheduleEmployment.text = tvSchedule
            tvDescription.text = formattedDescription
            tvNameOfVacancy.text = vacancy.title
            tvSalary.text = vacancy.salary
            tvNameOfCompany.text = vacancy.company
            tvArea.text = vacancy.area
            imageView.setImage(
                vacancy.logo,
                R.drawable.ic_placeholder_company,
                binding.root.context.resources.getDimensionPixelSize(R.dimen.size_12dp)
            )
        }
    }

    private fun showKeySkills(vacancy: VacancyFullInfo) {
        with(binding) {
            if (vacancy.keySkills.isEmpty()) {
                tvKeySkillsTitle.visibility = View.GONE
                tvKeySkills.visibility = View.GONE
            } else {
                tvKeySkills.text = vacancy.keySkills
            }
        }
    }

    private fun hideContactsIfEmpty(vacancy: VacancyFullInfo) {
        with(binding) {
            if (vacancy.contactName.isEmpty()) {
                tvContactsName.visibility = View.GONE
                tvContactsPerson.visibility = View.GONE
            }
            if (vacancy.contactEmail.isEmpty()) {
                tvContactsEmail.visibility = View.GONE
                tvEmail.visibility = View.GONE
            }
            if (vacancy.contactPhones.isEmpty()) {
                tvContactsPhone.visibility = View.GONE
                tvPhone.visibility = View.GONE
            }
            if (vacancy.contactComment.isEmpty()) {
                tvContactsComment.visibility = View.GONE
                tvComment.visibility = View.GONE
            }
            if (vacancy.contactName.isEmpty() &&
                vacancy.contactEmail.isEmpty() &&
                vacancy.contactPhones.isEmpty()
            ) {
                tvContactsTitle.visibility = View.GONE
            }
        }
    }

    fun renderOffline(message: String) {
        binding.scrollView.visibility = View.GONE
        binding.placeHolderText.text = message
        binding.placeHolder.visibility = View.VISIBLE
    }

    fun renderError(message: String) {
        binding.scrollView.visibility = View.GONE
        binding.placeHolderText.text = message
        binding.placeHolder.visibility = View.VISIBLE
    }

    fun renderLoading() {
        binding.scrollView.visibility = View.GONE
        binding.iwAnim.visibility = View.VISIBLE
    }

    fun renderAddAnimation(scope: CoroutineScope) {
        scope.launch {
            binding.lottieHeart.speed = STRAIGHT_ANIMATION_SPEED
            binding.lottieHeart.playAnimation()
        }
    }

    fun renderDeleteAnimation(scope: CoroutineScope) {
        scope.launch {
            binding.lottieHeart.speed = REVERS_ANIMATION_SPEED
            binding.lottieHeart.playAnimation()
        }
    }

    companion object {
        const val STRAIGHT_ANIMATION_SPEED = 4f
        const val REVERS_ANIMATION_SPEED = -4f
    }
}
