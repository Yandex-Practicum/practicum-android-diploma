package ru.practicum.android.diploma.network_client.data.dto

import ru.practicum.android.diploma.network_client.domain.models.HttpStatus

interface Response {
    var resultCode: HttpStatus
}
