package ru.practicum.android.diploma.ui.state

//  Импортировать Vacancy и ResponseError после того, как они будут добавлены в develop
// import ru.practicum.android.diploma.domain.models.Vacancy
// import ru.practicum.android.diploma.util.ResponseData.ResponseError

sealed interface SearchScreenState {

    data object Loading : SearchScreenState

    data object LoadNextPage : SearchScreenState

    data object Default : SearchScreenState

    data class NothingFound(
        //  Заменить List<Any> на List<Vacancy> после добавления Vacancy
        val vacancies: List<Any>,
        val found: Int
    ) : SearchScreenState

    data class Success(
        //  Заменить List<Any> на List<Vacancy> после добавления Vacancy
        val vacancies: List<Any>,
        val found: Int
    ) : SearchScreenState

    data class Error(
        //  Заменить Any на ResponseError после добавления ResponseError
        val error: Any
    ) : SearchScreenState
}
