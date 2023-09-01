package ru.practicum.android.diploma.details.ui

import androidx.core.text.HtmlCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.search.domain.models.VacancyFullInfoModel

sealed interface DetailsScreenState {
    fun render(binding: FragmentDetailsBinding)
    object Empty : DetailsScreenState{
        override fun render(binding: FragmentDetailsBinding) {
            TODO("Not yet implemented")
        }
    }
    data class Content(val vacancy: VacancyFullInfoModel) : DetailsScreenState{
        override fun render(binding: FragmentDetailsBinding) {
            with(binding) {
                val tvSchedule = vacancy.employment?.name + ". " + vacancy.schedule?.name
                val formattedDescription =
                    HtmlCompat.fromHtml(vacancy.description!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
                val tvKeySkillsList =
                    vacancy.keySkills?.mapIndexed { _, skill -> "• ${skill.name}" }
                        ?.joinToString("\n")
                val phoneList = vacancy.contacts?.phones?.mapIndexed { _, phone ->
                    "+" + phone.country +
                            " (${phone.city}) " + phone.number
                }?.joinToString("\n")
                tvExperience.text = vacancy.experience?.name
                tvScheduleEmployment.text = tvSchedule
                tvDescription.text = formattedDescription
                tvKeySkills.text = tvKeySkillsList
                tvContactsName.text = vacancy.contacts?.name
                tvContactsEmail.text = vacancy.contacts?.email
                tvContactsPhone.text = phoneList
            }
        }
    }

    class PlayHeartAnimation(private val isInFavs: Boolean, val scope: CoroutineScope) :
        DetailsScreenState {
        override fun render(binding: FragmentDetailsBinding) {
            scope.launch {
                if (isInFavs) {
                    binding.lottieHeart.speed = STRAIGHT_ANIMATION_SPEED
                    binding.lottieHeart.playAnimation()
                } else {
                    binding.lottieHeart.speed = REVERS_ANIMATION_SPEED
                    binding.lottieHeart.playAnimation()
                }
            }
        }
    }
}
const val STRAIGHT_ANIMATION_SPEED = 1f
const val REVERS_ANIMATION_SPEED = -1f