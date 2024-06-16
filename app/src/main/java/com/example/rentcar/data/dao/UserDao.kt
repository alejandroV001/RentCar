package com.example.rentcar.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.rentcar.data.models.user.UsersWithRole
import com.example.rentcar.models.user.RoleModel
import com.example.rentcar.models.user.RoleType
import com.example.rentcar.models.user.UserModel

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(model: UserModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRole(model: RoleModel)

    @Transaction
    @Query("SELECT * FROM RoleModel WHERE type = :roleType")
    fun getUsersWithRole(roleType: RoleType): LiveData<UsersWithRole>
}