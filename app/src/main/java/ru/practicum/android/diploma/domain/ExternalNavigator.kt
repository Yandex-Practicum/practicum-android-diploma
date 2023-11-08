package ru.practicum.android.diploma.domain

import androidx.recyclerview.widget.RecyclerView

interface ExternalNavigator {
    fun sharePhone(phone: RecyclerView)
    fun shareEmail(email: String)
}