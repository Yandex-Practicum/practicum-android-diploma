package ru.practicum.android.diploma.util

import android.animation.Animator

class AnimationUtils {
    abstract class AnimationEndListener : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) { /* ignore */ }

        override fun onAnimationCancel(animation: Animator) { /* ignore */ }

        override fun onAnimationRepeat(animation: Animator) { /* ignore */ }
    }
}