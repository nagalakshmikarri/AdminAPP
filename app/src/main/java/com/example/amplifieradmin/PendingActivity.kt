package com.example.amplifieradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.AdsPendingData
import com.example.amplifieradmin.data.model.AdsPendingResp
import com.example.amplifieradmin.databinding.ActivityHomeBinding
import com.example.amplifieradmin.databinding.ActivityPendingBinding
import com.example.amplifieradmin.databinding.PendingBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AdminUserAdapter
import com.example.amplifieradmin.ui.main.Adapter.AdsPendingAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PendingActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityPendingBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: AdsPendingAdapter
    private val binding get() = _binding!!
    var totalPages: Int = 0
    var currentPageNo: Int = 0
    private var subAdmin_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPendingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
        setupUI()
        setupViewModel()
        observeViewModel()
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
                    is MainState.AdsPending -> {
                        Log.e("testtt", it.AdsPendingResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = AdsPendingAdapter(
                            it.AdsPendingResp!!.data,
                            this@PendingActivity,
                            object : AdsPendingAdapter.OnAcceptClick {
                                override fun onAcceptClick(acceptdId: String, accept: String) {
                                    lifecycleScope.launch {
                                        homeViewModel.homeIntent.send(
                                            MainIntent.AdminAdsAccept(
                                                prefHelper.getString(Constants.PREF_ADMINID)!!,
                                                acceptdId
                                            )
                                        )
                                    }
                                }
                            },
                            object : AdsPendingAdapter.OnRejectClick {
                                override fun onRejectClick(rejectedId: String, reject: String) {
                                    lifecycleScope.launch {
                                        homeViewModel.homeIntent.send(
                                            MainIntent.AdminAdsReject(
                                                prefHelper.getString(Constants.PREF_ADMINID)!!,
                                                rejectedId
                                            )
                                        )
                                    }

                                }
                            },
                        object :AdsPendingAdapter.OnLinkClick{
                            override fun onLinkClick(adsPendingData: AdsPendingData?) {

                            }

                        })
                        binding.recyclerView.adapter = adapter
                        homeRenderList(it.AdsPendingResp)
                    }

                    is MainState.AdminAdsAccept -> {
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.AdsPending(
                                    prefHelper.getString(Constants.PREF_ADMINID)!!, subAdmin_id
                                )

                            )
                        }

                    }
                    is MainState.AdminAdsReject -> {
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.AdsPending(
                                    prefHelper.getString(Constants.PREF_ADMINID)!!, subAdmin_id
                                )

                            )
                        }

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

    private fun homeRenderList(adsPendingResp: AdsPendingResp) {
        if (adsPendingResp!!.data.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }
        adsPendingResp.let { listOfUsers -> listOfUsers.let { adapter.addData(it.data) } }
        adapter.notifyDataSetChanged()

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.AdsPending(
                    prefHelper.getString(Constants.PREF_ADMINID)!!, subAdmin_id
                )

            )
        }


    }

    private fun setupUI() {
        subAdmin_id = intent.getStringExtra("subadmin_id").toString()
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@PendingActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this@PendingActivity)
        prefHelper = PrefHelper(this)
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}