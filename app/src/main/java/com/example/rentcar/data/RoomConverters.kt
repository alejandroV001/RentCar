package com.example.rentcar.data

import androidx.room.TypeConverter
import com.example.rentcar.models.user.RoleType

class RoomConverters {
    @TypeConverter
    fun roleTypeToInt(roleType: RoleType): Int = roleType.key

    @TypeConverter
    fun intToRoleType(key: Int): RoleType = RoleType.fromKey(key)
}