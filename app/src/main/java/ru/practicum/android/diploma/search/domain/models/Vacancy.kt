package ru.practicum.android.diploma.search.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.DelegateAdapterItem

@Parcelize
@Serializable
data class Vacancy(
    val id: String,
    val iconUri: String = "",
    val title: String = "",
    val company: String = "",
    val salary: String = "",
    val area: String = "",
    val date : String = "",
) : Parcelable, DelegateAdapterItem
{
    override fun id(): Any {
        return id
    }

    override fun content(): Any {
        return this
    }
}



