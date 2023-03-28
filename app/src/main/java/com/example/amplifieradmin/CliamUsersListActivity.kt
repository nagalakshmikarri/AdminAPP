package com.example.amplifieradmin

import android.content.Intent
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
import com.example.amplifieradmin.data.model.CliamBusinessListResp
import com.example.amplifieradmin.data.model.CliamBusinessListRespData
import com.example.amplifieradmin.data.model.CliamDetailResp
import com.example.amplifieradmin.data.model.CliamDetailRespData
import com.example.amplifieradmin.databinding.ActivityCliamBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityCliamUsersListBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.CliamBusinessListAdapter
import com.example.amplifieradmin.ui.main.Adapter.CliamDetailAdaper
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class CliamUsersListActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityCliamUsersListBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: CliamDetailAdaper
    private val binding get() = _binding!!
    private var sID: String? = "0"
    private var sBusiness: String? = null
    private var sPhone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCliamUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupClicks()
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
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Error -> {
                        Log.e("testtt", "Not found/Error")
                        binding.progressBar.visibility = View.GONE
                        //Toast.makeText(context, R.string.datanotfound, Toast.LENGTH_LONG).show()
                    }
                    is MainState.CliamDetail -> {
                        Log.e("testtt", it.cliamDetailResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = CliamDetailAdaper(
                            it.cliamDetailResp!!.list,
                            this@CliamUsersListActivity,
                            object : CliamDetailAdaper.OnItemClick {
                                override fun onItemClick(cliamDetailRespData: CliamDetailRespData) {
                                    var intent=Intent(this@CliamUsersListActivity,UserDetailsActivity::class.java)
                                    intent.putExtra("user_details",cliamDetailRespData)
                                    startActivity(intent)
                                }

                            })
                        binding.cliamUsersRecyclerview.adapter = adapter
                        homeRenderList(it.cliamDetailResp)

                    }

                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(cliamDetailResp: CliamDetailResp) {
        if (cliamDetailResp!!.list.isNotEmpty()) {
            binding.cliamUsersRecyclerview.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.cliamUsersRecyclerview.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.CliamDetail(
                    sID
                )

            )

        }

    }

    private fun setupUI() {
        prefHelper = PrefHelper(this)
        sID = intent.getStringExtra("s_id").toString()
        sBusiness = intent.getStringExtra("s_business").toString()
        sPhone = intent.getStringExtra("s_phone").toString()
        binding.tvBusinessName.text = sBusiness
        binding.tvBusinessPhone.text = sPhone
        binding.cliamUsersRecyclerview.addItemDecoration(
            DividerItemDecoration(
                this@CliamUsersListActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.cliamUsersRecyclerview.layoutManager =
            LinearLayoutManager(this@CliamUsersListActivity)

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}