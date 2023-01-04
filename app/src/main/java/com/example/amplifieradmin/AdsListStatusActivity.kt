package com.example.amplifieradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.databinding.ActivityAdsListStatusBinding
import com.example.amplifieradmin.databinding.ActivityPendingBinding
import com.example.amplifieradmin.ui.main.Adapter.AdminUserAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdsListStatusActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityAdsListStatusBinding? = null
    private val binding get() = _binding!!
    private var subadmin_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAdsListStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
        setupUI()
        setupViewModel()
        observeViewModel()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            homeViewModel.state.collect {
                when(it){
                    is MainState.Idle -> {
                        Log.e("testtt", "Idle")

                    }
                    is MainState.Loading -> {
                        Log.e("testtt", "Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.SubAdminInfo -> {
                        Log.e("testtt", it.subAdminInfoResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        binding.tvSubAdminName.text=it.subAdminInfoResp.data.admin_name
                        binding.tvPhoneNumber.text=it.subAdminInfoResp.data.admin_phone
                        binding.tvAcceptCount.text=it.subAdminInfoResp.accepted.toString()
                        binding.tvRejectCount.text=it.subAdminInfoResp.rejected.toString()
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
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.SubAdminInfo(subadmin_id.toInt())

            )
        }

    }

    private fun setupUI() {
//        Toast.makeText(this, intent.getStringExtra("subadmin_id").toString(), Toast.LENGTH_SHORT).show()
        subadmin_id = intent.getStringExtra("subadmin_id").toString()
    }

    private fun setupClicks() {
        binding.tvPendingList.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, PendingActivity::class.java)
            intent.putExtra("subadmin_id",subadmin_id)
            startActivity(intent);
        }


        binding.referBusiness.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, RecommendBusinessActivity::class.java)
            startActivity(intent);
        }

        binding.tvAceeptads.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, AcceptAdsActivity::class.java)
            startActivity(intent);
        }
        binding.AdsRejected.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, RejectAcivity::class.java)
            startActivity(intent);
        }

        binding.BusinessList.setOnClickListener {
            val intent=Intent(this@AdsListStatusActivity,BusinessListActivity::class.java)
            startActivity(intent);
        }

        binding.CliamBusiness.setOnClickListener {
            val intent=Intent(this@AdsListStatusActivity,CliamBusinessActivity::class.java)
            startActivity(intent);
        }
    }
}