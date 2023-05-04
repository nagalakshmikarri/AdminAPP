package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.GettingRewardRespData
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.data.model.UpdateRewardsReq
import com.example.amplifieradmin.databinding.ActivityEditBinding
import com.example.amplifieradmin.databinding.ActivityRegistrationBinding
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityEditBinding? = null
    private  val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.updateBtn.setOnClickListener {
        val updateRewardsReq=UpdateRewardsReq(
            binding.etRegisterPoints.text.toString(),
            binding.etHomeScreenPoints.text.toString(),
            binding.etContact.text.toString()
        )

            if (checkValidations()) {
                lifecycleScope.launch {
                    homeViewModel.homeIntent.send(
                        MainIntent.UpdateRewards(
                            updateRewardsReq
                        )
                    )
                }
            }

        }

    }

    private fun checkValidations(): Boolean {
        if (binding.etRegisterPoints.text.toString().trim().isEmpty()) {
            binding.etRegisterPoints.error = "Please Enter Register Points"
            return false
        }

        if (binding.etHomeScreenPoints.text.toString().trim().isEmpty()) {
            binding.etHomeScreenPoints.error = "Please Enter HomeScreen Points "
            return false
        }

        if (binding.etContact.text.toString().trim().isEmpty()) {
            binding.etContact.error = "Please Enter Contact Points "
            return false
        }
        return true

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            homeViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                        Log.e("testtt", "Idle")

                    }
                    is MainState.Loading -> {
                        Log.e("testtt", "Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.UpdateRewards -> {
                        Log.e("testtt", it.updateRewardsResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@EditActivity,
                            "Update Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }

                    is MainState.Error -> {
                        Log.e("testtt", "Not found/Error")
                        binding.progressBar.visibility = View.GONE
                        //Toast.makeText(context, R.string.datanotfound, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

    }

    private fun setupUI() {
        val dataDetails: GettingRewardRespData?=intent.getParcelableExtra<GettingRewardRespData>("info")
        setData(dataDetails)
    }

    private fun setData(gettingRewardRespData: GettingRewardRespData?) {

        binding.etRegisterPoints.setText(gettingRewardRespData!!.register)
        binding.etHomeScreenPoints.setText(gettingRewardRespData!!.home_screen_view)
        binding.etContact.setText(gettingRewardRespData!!.friend_accept)

    }
}