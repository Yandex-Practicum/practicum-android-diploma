package ru.practicum.android.diploma.di.annotations

/*
эта аннотация значит, что класс создан просто для теста,
 в дальнейшем все такие классы удалятся или заменятся на актуальные
 например для теста ретрофита использую TrackDto из ITunesApi
 */
@TestClass
annotation class TestClass

