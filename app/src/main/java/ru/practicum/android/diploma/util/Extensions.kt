package ru.practicum.android.diploma.util

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

// системная кн Назад для всех фрагментов кроме mainFragment
fun Fragment.handleBackPress(popTo: Int? = null) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (popTo != null) {
                    findNavController().popBackStack(popTo, false)
                } else {
                    findNavController().popBackStack()
                }
            }
        }
    )
}
