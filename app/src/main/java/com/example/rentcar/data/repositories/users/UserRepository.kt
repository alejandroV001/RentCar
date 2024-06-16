package com.example.rentcar.data.repositories.users

import androidx.lifecycle.LiveData
import com.example.rentcar.data.models.user.UsersWithRole
import com.example.rentcar.models.user.UserModel

interface UserRepository {
    suspend fun insertUserWithRole(user: UserModel)

    fun getPlebUsers(): LiveData<UsersWithRole>
    fun getAdminUsers(): LiveData<UsersWithRole>
}