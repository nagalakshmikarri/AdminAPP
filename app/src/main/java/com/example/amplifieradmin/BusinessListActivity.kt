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
import com.example.amplifieradmin.data.model.BusinessListResp
import com.example.amplifieradmin.databinding.ActivityBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityHomeBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AcceptedAdsAdapter
import com.example.amplifieradmin.ui.main.Adapter.BusinessListAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class   BusinessListActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityBusinessListBinding? = null
    private lateinit var adapter: BusinessListAdapter
    private val binding get() = _binding!!
    lateinit var prefHelper: PrefHelper
    private var admin_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityBusinessListBinding.inflate(layoutInflater)
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
                    is MainState.BusinessList -> {
                        Log.e("testtt", it.businessListResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = BusinessListAdapter(
                            it.businessListResp!!.data,
                            this@BusinessListActivity,

)
                        binding.BusinessRecyclerview.adapter = adapter
                        homeRenderList(it.businessListResp)
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
    private fun homeRenderList(businessListResp: BusinessListResp) {
        if (businessListResp!!.data.isNotEmpty()) {
            binding.BusinessRecyclerview.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.BusinessRecyclerview.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.BusinessList(
                    "4"
                )

            )
        }

    }

    private fun setupUI() {
        admin_id = intent.getStringExtra("admin_id").toString()
        binding.BusinessRecyclerview.addItemDecoration(
            DividerItemDecoration(
                this@BusinessListActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.BusinessRecyclerview.layoutManager =
            LinearLayoutManager(this@BusinessListActivity)
        prefHelper = PrefHelper(this)


    }

    private fun setupClicks() {
        binding.addBusiness.setOnClickListener {
            val intent=Intent(this@BusinessListActivity,RegistrationActivity::class.java)
            startActivity(intent);
        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}