package ru.practicum.android.diploma.di.annotations

import javax.inject.Qualifier
//пример qulifier для каких то входных параметров для viewmodel
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IdQualifier
