package com.example.rentcar.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rentcar.BuildConfig
import com.android.volley.toolbox.StringRequest
import com.example.rentcar.R
import com.example.rentcar.databinding.FragmentLoginBinding
import com.example.rentcar.helpers.VolleyRequestQueue
import com.example.rentcar.helpers.extensions.logErrorMessage
import com.example.rentcar.managers.SharedPrefsManager
import com.example.rentcar.models.LoginModel
import com.example.rentcar.models.api.LoginAPIResponseModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), LoginFragmentListener {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)

        binding.listener = this
        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(BuildConfig.DEBUG) {
            viewModel.username.value = "mor_2314"
            viewModel.password.set("83r5^_")
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loginModel.observe(viewLifecycleOwner) { loginModel ->
            doLogin(loginModel)
        }
    }

    private fun handleLoginResponse(response: LoginAPIResponseModel) {
        SharedPrefsManager.writeToken(response.token)
        goToProducts()
    }

    private fun doLogin(loginModel: LoginModel) {
        val url = "${BuildConfig.BASE_URL}auth/login"

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            { response ->
                "success".logErrorMessage()
                viewModel.saveLoginUser(loginModel) // not a good practice

                val apiResponse = Gson().fromJson(response, LoginAPIResponseModel::class.java)
                handleLoginResponse(apiResponse)
            },
            {
                "That didn't work!".logErrorMessage()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = loginModel.username
                params["password"] = loginModel.password
                return params
            }
        }

        stringRequest.tag = "SRTAG"

        VolleyRequestQueue.addToRequestQueue(stringRequest)
    }

    override fun goToForgotPassword() {
        //
    }

    override fun goToRegister() =
        findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToRegisterFragment())

    private fun goToProducts() =
        findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToNavGraph())

    override fun onStop() {
        super.onStop()

        VolleyRequestQueue.requestQueue.cancelAll("SRTAG")
    }
}

interface LoginFragmentListener {
    fun goToForgotPassword()
    fun goToRegister()
}