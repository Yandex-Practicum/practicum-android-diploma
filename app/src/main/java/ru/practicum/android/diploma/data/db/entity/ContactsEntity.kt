package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo

data class ContactsEntity(
    @ColumnInfo(name = "name")
    val contactName: String?, // Имя контактного лица
    @ColumnInfo(name = "email")
    val contactEmail: String?, // Почта контакта
    @ColumnInfo(name = "phone_formatted")
    val contactPhoneFormatted: String? // Телефон контакта
)
