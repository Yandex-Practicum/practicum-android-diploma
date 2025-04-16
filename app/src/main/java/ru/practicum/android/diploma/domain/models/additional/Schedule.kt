package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.LabeledEnum

@Parcelize
enum class Schedule(val id: String, @StringRes override val labelResId: Int) : Parcelable, LabeledEnum {
    FullDay("fullDay", R.string.fullDay),
    Shift("shift", R.string.shift),
    Flexible("flexible", R.string.flexible),
    Remote("remote", R.string.remote),
    FlyInFlyOut("flyInFlyOut", R.string.flyInFlyOut);

    companion object {
        fun fromId(id: String?): Schedule? = when (id) {
            FullDay.id -> FullDay
            Shift.id -> Shift
            Flexible.id -> Flexible
            Remote.id -> Remote
            FlyInFlyOut.id -> FlyInFlyOut
            else -> null
        }

        fun all(): List<Schedule> = listOf(
            FullDay,
            Shift,
            Flexible,
            Remote,
            FlyInFlyOut
        )
    }
}
