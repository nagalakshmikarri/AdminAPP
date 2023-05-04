package com.example.amplifieradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.GettingRewardResp
import com.example.amplifieradmin.data.model.GettingRewardRespData
import com.example.amplifieradmin.data.model.RecommmendBusinnessResp
import com.example.amplifieradmin.databinding.ActivityAllBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityRewardBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.ui.main.Adapter.RejectedAdsAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class RewardActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityRewardBinding? = null
    private val binding get() = _binding!!
    private lateinit var gettingRewars: GettingRewardRespData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
        setupViewModel()
        observeViewModel()
        setupuI()
    }

    private fun setupuI() {

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
                    is MainState.GettingRewards -> {
                        Log.e("testtt", it.gettingRewardResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        binding.pointsTv.text=it.gettingRewardResp?.data!!.register+" "+"Ponits"
                        binding.homeScreenTv.text=it.gettingRewardResp?.data!!.home_screen_view+" "+"Ponits"
                        binding.contactTv.text=it.gettingRewardResp?.data!!.friend_accept+" "+"Ponits"
                        gettingRewars=it.gettingRewardResp?.data
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
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.GettingReward
            )

        }

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.editTv.setOnClickListener {
            val intent = Intent(this@RewardActivity, EditActivity::class.java)
            intent.putExtra("info",gettingRewars )
            startActivity(intent);
        }
    }
}