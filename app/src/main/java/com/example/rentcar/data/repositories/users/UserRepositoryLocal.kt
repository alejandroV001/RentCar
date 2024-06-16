package com.example.rentcar.data.repositories.users

import com.example.rentcar.data.dao.UserDao
import com.example.rentcar.models.user.RoleModel
import com.example.rentcar.models.user.RoleType
import com.example.rentcar.models.user.UserModel

class UserRepositoryLocal(private val dao: UserDao): UserRepository {
    override suspend fun insertUserWithRole(user: UserModel) {
        val role = RoleModel(user.role)

        dao.insertUser(user)
        dao.insertRole(role)
    }

    override fun getPlebUsers() = dao.getUsersWithRole(RoleType.USER_PLEB)
    override fun getAdminUsers() = dao.getUsersWithRole(RoleType.ADMIN)
}