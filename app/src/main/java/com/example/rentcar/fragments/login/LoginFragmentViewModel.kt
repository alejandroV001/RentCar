package com.example.rentcar.fragments.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.android.volley.BuildConfig
import com.example.rentcar.data.repositories.users.UserRepository
import com.example.rentcar.helpers.SingleLiveEvent
import com.example.rentcar.models.LoginModel
import com.example.rentcar.models.user.RoleType
import com.example.rentcar.models.user.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    val logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Google_Images_2015_logo.svg/1200px-Google_Images_2015_logo.svg.png"

    val username = MutableLiveData<String>()
    val password = ObservableField<String>()

    val isUsernameError: LiveData<Boolean> = username.switchMap { username ->
        val isUsernameValid = username.length <= 3
        MutableLiveData(isUsernameValid)
    }

    val loginModel = SingleLiveEvent<LoginModel>()

    fun generateLoginModel() {
        val username = this.username.value ?: ""
        val password = this.password.get() ?: ""

        loginModel.value = LoginModel(username, password)
    }

    fun saveLoginUser(loginModel: LoginModel) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.insertUserWithRole(UserModel(
            username = loginModel.username,
            password = loginModel.password,
            role = RoleType.ADMIN
        ))
    }

}