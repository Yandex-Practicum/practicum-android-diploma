package ru.practicum.android.diploma.root.data.network.models

data class Response<T>(
    var resultCode: NetworkResultCode = NetworkResultCode.UNKNOWN_ERROR,
    var data: T? = null
)