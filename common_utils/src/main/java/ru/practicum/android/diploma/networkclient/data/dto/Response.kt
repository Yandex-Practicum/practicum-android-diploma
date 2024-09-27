package ru.practicum.android.diploma.networkclient.data.dto

import ru.practicum.android.diploma.networkclient.domain.models.HttpStatus

interface Response {
    val resultCode: HttpStatus
}
