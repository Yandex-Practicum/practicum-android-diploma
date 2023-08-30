package ru.practicum.android.diploma.features.vacancydetails.data.models

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.root.data.network.models.Response

class VacancyDetailsResponse(@SerializedName("results") val searchResult: VacancyDetailsDto) : Response()

