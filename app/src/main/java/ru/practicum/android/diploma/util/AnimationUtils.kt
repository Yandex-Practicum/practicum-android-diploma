package ru.practicum.android.diploma.util

import android.animation.Animator

class AnimationUtils {
    abstract class AnimationEndListener : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            // Ignore
        }

        override fun onAnimationCancel(animation: Animator) {
            // Ignore
        }

        override fun onAnimationRepeat(animation: Animator) {
            // Ignore
        }
    }
}