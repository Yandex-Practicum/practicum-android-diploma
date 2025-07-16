package ru.practicum.android.diploma.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import ru.practicum.android.diploma.domain.models.salary.Salary

class TypeConverter {

    @TypeConverter
    fun fromSalary(salary: Salary): String {
        val json = JSONObject()

        when (salary) {
            is Salary.NotSpecifies -> {
                json.put("type", "NotSpecifies")
            }
            is Salary.From -> {
                json.put("type", "From")
                json.put("amount", salary.amount)
                json.put("currency", salary.currency)
            }
            is Salary.Range -> {
                json.put("type", "Range")
                json.put("from", salary.from)
                json.put("to", salary.to)
                json.put("currency", salary.currency)
            }
            is Salary.Fixed -> {
                json.put("type", "Fixed")
                json.put("amount", salary.amount)
                json.put("currency", salary.currency)
            }
        }
        return json.toString()
    }

    @TypeConverter
    fun toSalary(json: String): Salary {
        val obj = JSONObject(json)
        val type = obj.optString("type", null) ?: throw JSONException("Missing 'type' field in Salary JSON")
        return when (type) {
            "NotSpecifies" -> Salary.NotSpecifies
            "From" -> Salary.From(
                obj.getInt("amount"),
                obj.getString("currency")
            )
            "Range" -> Salary.Range(
                obj.getInt("from"),
                obj.getInt("to"),
                obj.getString("currency")
            )
            "Fixed" -> Salary.Fixed(
                obj.getInt("amount"),
                obj.getString("currency")
            )
            else -> throw IllegalArgumentException("Unknown salary type")
        }
    }
}
