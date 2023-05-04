package com.example.amplifieradmin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.RejectAdsData
import com.example.amplifieradmin.data.model.RejectAdsResp
import com.example.amplifieradmin.databinding.ActivityRejectAcivityBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.RejectedAdsAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import com.google.android.libraries.places.internal.it
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RejectAcivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityRejectAcivityBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: RejectedAdsAdapter
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityRejectAcivityBinding.inflate(layoutInflater)
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
                    is MainState.SubAdminAdsReject -> {
                        Log.e("testtt", it.rejectAdsResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = RejectedAdsAdapter(
                            it.rejectAdsResp!!.data,
                            this@RejectAcivity,
                            object: RejectedAdsAdapter.OnAcceptClick{
                                override fun onAcceptClick(acceptdId: String, accept: String) {
                                    lifecycleScope.launch {
                                        homeViewModel.homeIntent.send(
                                            MainIntent.AdminAdsAccept(
                                                prefHelper.getString(Constants.PREF_ADMINID)!!
                                                ,acceptdId
                                            )
                                        )
                                    }
                                }
                            },
                            object :RejectedAdsAdapter.OnLinkClick{
                                override fun onLinkClick(
                                    rejectAdsData: RejectAdsData?
                                ) {

                                }

                            }
                            )
                        binding.rejectRecyclerview.adapter = adapter
                        homeRenderList(it.rejectAdsResp)
                    }
                    is MainState.AdminAdsAccept ->{
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.SubAdminAdsReject(4
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

    private fun homeRenderList(rejectAdsResp: RejectAdsResp) {
        if (rejectAdsResp!!.data.isNotEmpty()) {
            binding.rejectRecyclerview.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.rejectRecyclerview.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }
        rejectAdsResp.let { listOfUsers -> listOfUsers.let { adapter.addData(it.data) } }
        adapter.notifyDataSetChanged()

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.SubAdminAdsReject(
                    prefHelper.getString(Constants.PREF_ADMINID)!!.toInt()
                )

            )

        }

    }

    private fun setupUI() {
        binding.rejectRecyclerview.addItemDecoration(
            DividerItemDecoration(
                this@RejectAcivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rejectRecyclerview.layoutManager = LinearLayoutManager(this@RejectAcivity)
        prefHelper = PrefHelper(this)
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

}