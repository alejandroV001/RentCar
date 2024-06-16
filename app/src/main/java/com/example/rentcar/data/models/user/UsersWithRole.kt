package com.example.rentcar.data.models.user

import androidx.room.Embedded
import androidx.room.Relation
import com.example.rentcar.models.user.RoleModel
import com.example.rentcar.models.user.RoleModel.Companion.ARG_TYPE
import com.example.rentcar.models.user.UserModel
import com.example.rentcar.models.user.UserModel.Companion.ARG_ID
import com.example.rentcar.models.user.UserModel.Companion.ARG_ROLE

class UsersWithRole(
    @Embedded val role: RoleModel,
    @Relation(
        parentColumn = ARG_TYPE,
        entityColumn = ARG_ROLE
    ) val users: List<UserModel>
)