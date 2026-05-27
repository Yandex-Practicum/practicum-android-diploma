package ru.practicum.android.diploma.core.data.network

open class Response {
    var resultCode: Int = ResultCode.UNDEFINED
    var data: Any? = null
}

object ResultCode {
    const val UNDEFINED = 0
    const val SUCCESS = 200
    const val NO_INTERNET = -1
    const val CLIENT_ERROR = 400
    const val SERVER_ERROR = 500
}
