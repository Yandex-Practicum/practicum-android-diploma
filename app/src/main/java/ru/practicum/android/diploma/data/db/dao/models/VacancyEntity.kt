import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_favorite_vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val vacancyName: String,
    val companyName: String,
    @ColumnInfo(name = "alternate_url")
    val alternateUrl: String?,
    @ColumnInfo(name = "logo_url")
    val logoUrl: String?,
    val area: String?,
    val employment: String?,
    val experience: String?,
    @Embedded(prefix = "salary_")
    val salary: SalaryEntity?,
    val description: String?,
    @ColumnInfo(name = "key_skills")
    val keySkills: List<String?>,
    @Embedded(prefix = "contacts_")
    val contacts: ContactsEntity?,
    val comment: String?,
    val schedule: String?,
    val address: String?
)

data class SalaryEntity(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class ContactsEntity(
    val email: String?,
    val name: String?,
    val phones: List<PhoneEntity?>?
)

data class PhoneEntity(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)
