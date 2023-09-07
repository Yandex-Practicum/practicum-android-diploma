package ru.practicum.android.diploma.util.functional

sealed class Failure(val code: Int) {
    class NetworkConnection(code: Int) : Failure(code)
    class ServerError(code: Int) : Failure(code)
    abstract class AppFailure(code: Int) : Failure(code)

}