package ru.practicum.android.diploma.util

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun FragmentContainerView.applySystemBarsPadding(bottomNavigationView: BottomNavigationView) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        view.setPadding(
            0,
            systemBars.top,
            0,
            if (!bottomNavigationView.isVisible) systemBars.bottom else 0
        )

        insets
    }
}
