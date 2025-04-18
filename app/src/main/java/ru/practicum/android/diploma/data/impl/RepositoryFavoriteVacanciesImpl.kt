package ru.practicum.android.diploma.data.impl

//class RepositoryFavoriteVacanciesImpl(
//    private val vacancyDao: VacancyDao,
//) : RepositoryFavoriteVacancies {
//
//    override suspend fun insertVacancy(vacancy: VacancyShort): Result<Unit> {
//        return try {
//            val dataEntity = domainToData(vacancy)
//            vacancyDao.insertVacancy(dataEntity)
//            Result.success(Unit)
//        } catch (e: SQLiteException) {
//            Result.failure(e)
//        }
//    }
//
//    override suspend fun getAllVacancies(): List<VacancyShort> {
//        val vacanciesDataList = vacancyDao.getAll()
//        val vacanciesDomainList = vacanciesDataList.map { dataToDomain(it) }
//        return vacanciesDomainList
//    }
//
//    override suspend fun getById(vacancyId: Int): Result<VacancyShort> {
//        return try {
//            val vacancyData = vacancyDao.getById(vacancyId)
//            if (vacancyData != null) {
//                Result.success(dataToDomain(vacancyData))
//            } else {
//                Result.failure(Exception("Вакансия не найдена"))
//            }
//        } catch (e: SQLiteException) {
//            Result.failure(e)
//        }
//    }
//
//    override suspend fun deleteById(vacancyId: Int): Result<Unit> {
//        return try {
//            vacancyDao.deleteById(vacancyId)
//            Result.success(Unit)
//        } catch (e: SQLiteException) {
//            Result.failure(e)
//        }
//    }
//
//    override fun getVacanciesFlow(): Flow<List<VacancyShort>> {
//        return vacancyDao.getAllFlow()
//            .map { list -> list.map { dataToDomain(it) } }
//    }
//
//    override suspend fun clearDatabase(): Result<Unit> {
//        return try {
//            vacancyDao.clear()
//            Result.success(Unit)
//        } catch (e: SQLiteException) {
//            Result.failure(e)
//        }
//    }
//
//    private fun domainToData(vacancy: VacancyShort): VacancyShortDbEntity {
//        return VacancyShortDbEntity(
//            vacancyId = vacancy.vacancyId,
//            logoUrl = vacancy.logoUrl?.original,
//            name = vacancy.name,
//            areaName = vacancy.area,
//            employerName = vacancy.employer,
//            salary = vacancy.salary,
//            postedAt = vacancy.postedAt
//        )
//    }
//
//    private fun dataToDomain(vacancy: VacancyShortDbEntity): VacancyShort {
//        return VacancyShort(
//            vacancyId = vacancy.vacancyId,
//            logoUrl = LogoUrls(original = vacancy.logoUrl),
//            name = vacancy.name,
//            area = vacancy.areaName,
//            employer = vacancy.employerName,
//            salary = vacancy.salary ?: "Зарплата не указана",
//            postedAt = vacancy.postedAt,
//        )
//    }
//}
