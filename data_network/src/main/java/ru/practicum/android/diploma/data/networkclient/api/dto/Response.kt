package ru.practicum.android.diploma.data.networkclient.api.dto

import ru.practicum.android.diploma.search.domain.model.HttpStatus

interface Response {
    val resultCode: HttpStatus
}
