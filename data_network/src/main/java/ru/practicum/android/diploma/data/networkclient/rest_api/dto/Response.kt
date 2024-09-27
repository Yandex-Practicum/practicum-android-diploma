package ru.practicum.android.diploma.data.networkclient.rest_api.dto

import ru.practicum.android.diploma.search.domain.model.HttpStatus

interface Response {
    val resultCode: HttpStatus
}
