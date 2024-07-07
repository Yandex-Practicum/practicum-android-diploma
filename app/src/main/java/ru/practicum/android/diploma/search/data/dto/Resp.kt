package ru.practicum.android.diploma.search.data.dto

const val RESULT_CODE_NO_INTERNET = -1
const val REST_SUCCESS = 200
const val REST_BAD_REQUEST = 400
const val REST_SERVER_ERROR = 500

open class Resp {
    var resultCode = 0
}
