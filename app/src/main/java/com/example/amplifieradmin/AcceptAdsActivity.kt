package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.AcceptAdsData
import com.example.amplifieradmin.data.model.AcceptAdsResp
import com.example.amplifieradmin.databinding.ActivityAcceptAdsBinding
import com.example.amplifieradmin.databinding.ActivityPendingBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AcceptedAdsAdapter
import com.example.amplifieradmin.ui.main.Adapter.AdsPendingAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AcceptAdsActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityAcceptAdsBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: AcceptedAdsAdapter
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAcceptAdsBinding.inflate(layoutInflater)
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
                    is MainState.SubAdminAdsAccept -> {
                        Log.e("testtt", it.acceptAdsResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = AcceptedAdsAdapter(
                            it.acceptAdsResp!!.data,
                            this@AcceptAdsActivity,

                            object : AcceptedAdsAdapter.OnRejectClick {
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

                            object :AcceptedAdsAdapter.OnLinkClick{
                                override fun onLinkClick(
                                    acceptAdsData: AcceptAdsData?
                                ) {
                                }

                            }
                            )
                        binding.acceptRecyclerview.adapter = adapter
                        homeRenderList(it.acceptAdsResp)
                    }
                    is MainState.Error -> {
                        Log.e("testtt", "Not found/Error")
                        binding.progressBar.visibility = View.GONE
                        //Toast.makeText(context, R.string.datanotfound, Toast.LENGTH_LONG).show()
                    }
                    is MainState.AdminAdsReject -> {
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.SubAdminAdsAccept(
                                    4
                                )

                            )
                        }
                    }

                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(acceptAdsResp: AcceptAdsResp) {
        if (acceptAdsResp!!.data.isNotEmpty()) {
            binding.acceptRecyclerview.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.acceptRecyclerview.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }
        acceptAdsResp.let { listOfUsers -> listOfUsers.let { adapter.addData(it.data) } }
        adapter.notifyDataSetChanged()

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.SubAdminAdsAccept(
                    4
                )

            )

        }


    }

    private fun setupUI() {
        binding.acceptRecyclerview.addItemDecoration(
            DividerItemDecoration(
                this@AcceptAdsActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.acceptRecyclerview.layoutManager = LinearLayoutManager(this@AcceptAdsActivity)
        prefHelper = PrefHelper(this)
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}