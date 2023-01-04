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
import com.example.amplifieradmin.data.model.AdminUsersResp
import com.example.amplifieradmin.databinding.ActivityHomeBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AdminUserAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import com.google.android.libraries.places.internal.it
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityHomeBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: AdminUserAdapter
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
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
                    is MainState.AdminUser -> {
                        Log.e("testtt", it.adminUsersResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = AdminUserAdapter(
                            it.adminUsersResp!!.data,
                            this@HomeActivity
                        )
                        binding.recyclerView.adapter = adapter
                        homeRenderList(it.adminUsersResp)
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

    private fun homeRenderList(adminUsersResp: AdminUsersResp) {
        if (adminUsersResp!!.data.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }
        adminUsersResp.let { listOfUsers -> listOfUsers.let { adapter.addData(it.data) } }
        adapter.notifyDataSetChanged()
    }


    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.AdminUser

            )
        }
    }

    private fun setupUI() {
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@HomeActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
        prefHelper = PrefHelper(this)
        Log.e("SubAdminId", prefHelper.getString(Constants.PREF_ADMINID)!!)
    }

    private fun setupClicks() {
    }
}