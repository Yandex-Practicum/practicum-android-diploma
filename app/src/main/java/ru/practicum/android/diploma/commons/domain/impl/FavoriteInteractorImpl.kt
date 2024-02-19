package ru.practicum.android.diploma.commons.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commons.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.Details
import ru.practicum.android.diploma.domain.models.VacancyModel

class FavoriteInteractorImpl : FavoriteInteractor {

    override fun getMockResults(): Flow<ArrayList<VacancyModel>> {
        val vacancies = arrayListOf<VacancyModel>(
            VacancyModel(
                id = "1",
                vacancyName = "Software Engineer",
                city = "San Francisco",
                salary = "$100,000 - $120,000",
                companyName = "Tech Co.",
                logoUrls = arrayListOf("https://example.com/logo1.png"),
                details = Details(
                    experience = "3+ years",
                    employment = "Full-time",
                    description = "We are seeking a talented software engineer to join our team.",
                    keySkills = arrayListOf("Java", "Kotlin", "Android", "Git"),
                    managerName = "John Doe",
                    email = "john.doe@example.com",
                    phonesAndComments = arrayListOf(
                        Pair("123-456-7890", "Call for inquiries"),
                        Pair("987-654-3210", "Text only")
                    )
                )
            ),
            VacancyModel(
                id = "2",
                vacancyName = "Web Developer",
                city = "New York",
                salary = "$90,000 - $110,000",
                companyName = "Web Co.",
                logoUrls = arrayListOf("https://example.com/logo2.png"),
                details = Details(
                    experience = "2+ years",
                    employment = "Remote",
                    description = "We are looking for a skilled web developer to join our remote team.",
                    keySkills = arrayListOf("HTML", "CSS", "JavaScript", "React"),
                    managerName = "Jane Smith",
                    email = "jane.smith@example.com",
                    phonesAndComments = arrayListOf(
                        Pair("111-222-3333", "Call for details")
                    )
                )
            )
            // Add more VacancyModel objects if needed
        )



        return flow {
            emit(vacancies)
        }
    }
}