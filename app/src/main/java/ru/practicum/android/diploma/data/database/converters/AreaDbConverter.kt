package ru.practicum.android.diploma.data.database.converters

import ru.practicum.android.diploma.data.database.entity.AreaEntity
import ru.practicum.android.diploma.domain.models.Area

class AreaDbConverter {
    fun map(area: Area): AreaEntity {
        return AreaEntity(
            id = area.id,
            name = area.name
        )
    }

    fun map(area: AreaEntity): Area {
        return Area(
            id = area.id,
            name = area.name
        )
    }
}
