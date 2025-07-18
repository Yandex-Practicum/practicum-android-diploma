package ru.practicum.android.diploma.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.domain.models.vacancydetails.EmploymentForm

class TypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSalary(salary: Salary): String {
        val json = JSONObject()

        when (salary) {
            is Salary.NotSpecifies -> {
                json.put(TYPE, "NotSpecifies")
            }
            is Salary.From -> {
                json.put(TYPE, "From")
                json.put(AMOUNT, salary.amount)
                json.put(CURRENCY, salary.currency)
            }
            is Salary.Range -> {
                json.put(TYPE, "Range")
                json.put(FROM, salary.from)
                json.put(TO, salary.to)
                json.put(CURRENCY, salary.currency)
            }
            is Salary.Fixed -> {
                json.put(TYPE, "Fixed")
                json.put(AMOUNT, salary.amount)
                json.put(CURRENCY, salary.currency)
            }
        }
        return json.toString()
    }

    @TypeConverter
    fun toSalary(json: String): Salary {
        val obj = JSONObject(json)
        val type = obj.optString(TYPE, null) ?: throw JSONException("Missing 'type' field in Salary JSON")
        return when (type) {
            "NotSpecifies" -> Salary.NotSpecifies
            "From" -> Salary.From(
                obj.getInt(AMOUNT),
                obj.getString(CURRENCY)
            )
            "Range" -> Salary.Range(
                obj.getInt(FROM),
                obj.getInt(TO),
                obj.getString(CURRENCY)
            )
            "Fixed" -> Salary.Fixed(
                obj.getInt(AMOUNT),
                obj.getString(CURRENCY)
            )
            else -> throw IllegalArgumentException("Unknown salary type")
        }
    }

    @TypeConverter
    fun fromEmploymentForm(employmentForm: EmploymentForm?): String? {
        return employmentForm?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toEmploymentForm(json: String?): EmploymentForm? {
        return json?.let {
            gson.fromJson(it, EmploymentForm::class.java)
        }
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        return json?.let {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson<List<String>>(it, type)
        }
    }

    companion object {
        private const val TYPE = "type"
        private const val AMOUNT = "amount"
        private const val CURRENCY = "currency"
        private const val TO = "to"
        private const val FROM = "from"
    }
}
