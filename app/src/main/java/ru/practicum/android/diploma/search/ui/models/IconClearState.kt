package ru.practicum.android.diploma.search.ui.models

import androidx.appcompat.content.res.AppCompatResources
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding

class IconClearState(
    val query: String?,
) {
    fun render(binding: FragmentSearchBinding) {
        with(binding) {
            val context = searchIcon.context
            
            if (query.isNullOrEmpty()) {
                searchIcon.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context, R.drawable.ic_search
                    )
                )
            } else {
                searchIcon.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context, R.drawable.ic_clear
                    )
                )
            }
        }
    }
}