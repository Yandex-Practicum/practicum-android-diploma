package ru.practicum.android.diploma.data.networkclient.data.dto

import ru.practicum.android.diploma.data.networkclient.domain.models.HttpStatus

interface Response {
    val resultCode: HttpStatus
}
