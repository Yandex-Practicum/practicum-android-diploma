package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class HHVacancyDetailResponse(
    @SerializedName("accept_handicapped") val acceptHandicapped: Boolean,
    @SerializedName("accept_incomplete_resumes") val acceptIncompleteResumes: Boolean,
    @SerializedName("accept_kids") val acceptKids: Boolean,
    @SerializedName("accept_temporary") val acceptTemporary: Boolean,
    val address: Address,
    @SerializedName("allow_messages") val allowMessages: Boolean,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("apply_alternate_url") val applyAlternateUrl: String,
    val approved: Boolean,
    val archived: Boolean,
    val area: Area,
    @SerializedName("billing_type") val billingType: BillingType,
    @SerializedName("branded_description") val brandedDescription: String,
    @SerializedName("branded_template") val brandedTemplate: BrandedTemplate,
    @SerializedName("can_upgrade_billing_type") val canUpgradeBillingType: Boolean,
    val code: String,
    val contacts: Contacts,
    @SerializedName("created_at") val createdAt: String,
    val department: Department,
    val description: String,
    @SerializedName("driver_license_types") val driverLicenseTypes: List<DriverLicenseType>,
    val employer: Employer,
    val employment: Employment,
    val experience: Experience,
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("has_test") val hasTest: Boolean,
    val hidden: Boolean,
    val id: String,
    @SerializedName("initial_created_at") val initialCreatedAt: String,
    @SerializedName("insider_interview") val insiderInterview: InsiderInterview,
    @SerializedName("key_skills") val keySkills: List<KeySkill>,
    val languages: List<Language>,
    val manager: Manager,
    val name: String,
    val premium: Boolean,
    @SerializedName("previous_id") val previousId: String,
    @SerializedName("professional_roles") val professionalRoles: List<ProfessionalRole>,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("response_letter_required") val responseLetterRequired: Boolean,
    @SerializedName("response_notifications") val responseNotifications: Boolean,
    @SerializedName("response_url") val responseUrl: String?,
    val salary: Salary,
    val schedule: Schedule,
    val test: Test,
    val type: Type,
    @SerializedName("video_vacancy") val videoVacancy: VideoVacancy,
    @SerializedName("working_days") val workingDays: List<WorkingDay>,
    @SerializedName("working_time_intervals") val workingTimeIntervals: List<WorkingTimeInterval>,
    @SerializedName("working_time_modes") val workingTimeModes: List<WorkingTimeMode>,
    override val resultCode: HttpStatus = HttpStatus.OK,
) : Response, Parcelable
