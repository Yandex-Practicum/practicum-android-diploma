package ru.practicum.android.diploma.networkClient.data.dto

import ru.practicum.android.diploma.networkClient.domain.models.HttpStatus

interface Response {
    var resultCode: HttpStatus
}
