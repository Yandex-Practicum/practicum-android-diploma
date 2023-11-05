package ru.practicum.android.diploma.data.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.domain.models.mok.EmployerModel
import ru.practicum.android.diploma.domain.models.mok.Salary


data class FullVacancyDto (
    val name: String?,
    val address: Adress,
    @SerializedName("branded_description")
    val brandedDescription: String?,
    val contacts: Contacts,
    val description: String?,
    val employer: EmployerModel?,
    val experience: Experience?,
    val salary: Salary?,
    @SerializedName("key_skills")
    val keySkills: List<SkillName>
)

fun parseFullVacancy(jsonStr: String): FullVacancyDto? {
    val gson = Gson()
    return gson.fromJson(jsonStr, FullVacancyDto::class.java)
}

data class SkillName(
    val name: String?
)

data class Adress(
    val city: String,
)

data class Contacts(
    val email: String?,
    val name: String?,
    val phones: List<Phone>,
)

data class Phone(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)

data class Experience(
    val id: String?,
    val name: String?,
)

fun getFullVacancy(): FullVacancyDto? {
    val jsonStr = """
        {
  "accept_handicapped": false,
  "accept_incomplete_resumes": false,
  "accept_kids": false,
  "accept_temporary": false,
  "address": {
    "building": "9с10",
    "city": "Москва",
    "description": "на проходной потребуется паспорт",
    "lat": 55.807794,
    "lng": 37.638699,
    "metro_stations": [
      {
        "lat": 55.807794,
        "line_id": "6",
        "line_name": "Калужско-Рижская",
        "lng": 37.638699,
        "station_id": "6.8",
        "station_name": "Алексеевская"
      }
    ],
    "street": "улица Годовикова"
  },
  "allow_messages": true,
  "alternate_url": "https://hh.ru/vacancy/8331228",
  "apply_alternate_url": "https://hh.ru/applicant/vacancy_response?vacancyId=8331228",
  "archived": false,
  "area": {
    "id": "1",
    "name": "Москва",
    "url": "https://api.hh.ru/areas/1"
  },
  "billing_type": {
    "id": "standard",
    "name": "Стандарт"
  },
  "branded_description": "<style>...</style><div>...</div><script></script>",
  "branded_template": {
    "id": "1",
    "name": "test"
  },
  "can_upgrade_billing_type": true,
  "code": "HRR-3487",
  "contacts": {
    "email": "user@example.com",
    "name": "Имя",
    "phones": [
      {
        "city": "985",
        "comment": null,
        "country": "7",
        "number": "000-00-00"
      }
    ]
  },
  "created_at": "2013-07-08T16:17:21+0400",
  "department": {
    "id": "HH-1455-TECH",
    "name": "HeadHunter::Технический департамент"
  },
  "description": "...",
  "driver_license_types": [
    {
      "id": "A"
    },
    {
      "id": "B"
    }
  ],
  "employer": {
    "alternate_url": "https://hh.ru/employer/1455",
    "blacklisted": false,
    "id": "1455",
    "logo_urls": {
      "90": "https://hh.ru/employer-logo/289027.png",
      "240": "https://hh.ru/employer-logo/289169.png",
      "original": "https://hh.ru/file/2352807.png"
    },
    "name": "HeadHunter",
    "trusted": true,
    "url": "https://api.hh.ru/employers/1455"
  },
  "employment": {
    "id": "full",
    "name": "Полная занятость"
  },
  "experience": {
    "id": "between1And3",
    "name": "От 1 года до 3 лет"
  },
  "expires_at": "2013-08-08T16:17:21+0400",
  "has_test": true,
  "hidden": false,
  "id": "8331228",
  "initial_created_at": "2013-06-08T16:17:21+0400",
  "insider_interview": {
    "id": "12345",
    "url": "https://hh.ru/interview/12345?employerId=777"
  },
  "key_skills": [
    {
      "name": "Прием посетителей"
    },
    {
      "name": "Первичный документооборот"
    }
  ],
  "languages": [
    {
      "id": "eng",
      "level": {
        "id": "b2",
        "name": "B2 — Средне-продвинутый"
      },
      "name": "Английский"
    }
  ],
  "manager": {
    "id": "1"
  },
  "name": "Секретарь",
  "premium": true,
  "previous_id": "123456",
  "professional_roles": [
    {
      "id": "96",
      "name": "Программист, разработчик"
    }
  ],
  "published_at": "2013-07-08T16:17:21+0400",
  "response_letter_required": true,
  "response_notifications": true,
  "response_url": null,
  "salary": {
    "currency": "RUR",
    "from": 30000,
    "gross": true,
    "to": null
  },
  "schedule": {
    "id": "fullDay",
    "name": "Полный день"
  },
  "test": {
    "required": false
  },
  "type": {
    "id": "open",
    "name": "Открытая"
  },
  "video_vacancy": {
    "video_url": "https://host/video/123"
  },
  "working_days": [
    {
      "id": "only_saturday_and_sunday",
      "name": "Работа только по сб и вс"
    }
  ],
  "working_time_intervals": [
    {
      "id": "from_four_to_six_hours_in_a_day",
      "name": "Можно работать сменами по 4-6 часов в день"
    }
  ],
  "working_time_modes": [
    {
      "id": "start_after_sixteen",
      "name": "Можно начинать работать после 16-00"
    }
  ]
}
    """
    return parseFullVacancy(jsonStr)
}
