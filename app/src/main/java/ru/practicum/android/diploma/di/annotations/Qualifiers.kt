package ru.practicum.android.diploma.di.annotations

import javax.inject.Qualifier
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppEmail
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PrefsKey

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class NewResponse