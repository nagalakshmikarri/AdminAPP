package com.example.amplifieradmin

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.Data
import com.example.amplifieradmin.data.model.LoginResp
import com.example.amplifieradmin.databinding.ActivityLoginBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.LoginViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    lateinit var prefHelper: PrefHelper
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    override fun onStart() {
        super.onStart()
        if (prefHelper.getBoolean(Constants.PREF_IS_LOGIN)) {
            moveIntent()
        }
    }

    private fun moveIntent() {
        val intent = Intent(this, AdsListStatusActivity::class.java)
        intent.putExtra("subadmin_id", prefHelper.getString(Constants.PREF_ADMINID))
        startActivity(intent)
        finish()
    }

    private fun setupUI() {
        prefHelper = PrefHelper(this)

    }

    private fun saveSession(loginResp: Data) {
        prefHelper.put(Constants.PREF_ADMINID, loginResp.admin_id)
        prefHelper.put(Constants.PREF_ADMINNAME, loginResp.admin_name)
        prefHelper.put(Constants.PREF_ADMINPHONE, loginResp.admin_phone)
        prefHelper.put(Constants.PREF_IS_LOGIN, true)
    }

    private fun setupViewModel() {
        loginViewModel = androidx.lifecycle.ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        ).get(LoginViewModel::class.java)

    }

    private fun observeViewModel() {
        lifecycleScope.launch {

            loginViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                        Log.e("LoginIdel", "Login: ")
                    }
                    is MainState.Loading -> {
                        Log.e("LoginIdel", "Loading: ")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.LoginUser -> {
                        Log.e("LoginIdel", "Success: ")
                        binding.progressBar.visibility = View.GONE

                        if (it.loginResp.status == "ok") {
                            val intent = Intent(this@LoginActivity, AdsListStatusActivity::class.java)
                            intent.putExtra("subadmin_id", it.loginResp.data[0].admin_id)
                            startActivity(intent)
                            saveSession(it.loginResp.data[0])

                        } else {
                            Toast.makeText(this@LoginActivity, it.loginResp.msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Log.e("Error", "Failed to Login" + it.error)
                        Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupClicks() {
        binding.visibleEye.setOnClickListener {
            binding.inVisibleEye.visibility = View.VISIBLE
            binding.visibleEye.visibility = View.GONE
            binding.passcode.transformationMethod = HideReturnsTransformationMethod()
        }
        binding.inVisibleEye.setOnClickListener {
            binding.visibleEye.visibility = View.VISIBLE
            binding.inVisibleEye.visibility = View.GONE
            binding.passcode.transformationMethod = PasswordTransformationMethod()
        }
        binding.loginButton.setOnClickListener {
            if (checkValidations()) {
                lifecycleScope.launch {
                    loginViewModel.loginIntent.send(
                        MainIntent.LoginUser(
                            binding.userName.text.toString(),
                            binding.passcode.text.toString()

                        )

                    )

                }
            }
        }
    }

    private fun checkValidations(): Boolean {
        if (binding.userName.text.toString().trim().isEmpty()) {
            binding.userName.error = " Please Enter User Name "
            return false
        }
        return true;
    }

    private fun showMessage(mgs: String?) {

        Toast.makeText(this@LoginActivity, mgs, Toast.LENGTH_SHORT).show()

        //  AlertUtil.showSnackBar(binding.constraintLay, mgs)

    }

}

