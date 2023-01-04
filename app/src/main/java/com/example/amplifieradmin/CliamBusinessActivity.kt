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
import com.example.amplifieradmin.data.model.CliamBusinessResp
import com.example.amplifieradmin.databinding.ActivityCliamBusinessBinding
import com.example.amplifieradmin.databinding.ActivityPendingBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AdsPendingAdapter
import com.example.amplifieradmin.ui.main.Adapter.CliamBusinessAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CliamBusinessActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityCliamBusinessBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: CliamBusinessAdapter
    private val binding get() = _binding!!
    private var admin_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCliamBusinessBinding.inflate(layoutInflater)
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

                    is MainState.Error -> {
                        Log.e("testtt", "Not found/Error")
                        binding.progressBar.visibility = View.GONE
                        //Toast.makeText(context, R.string.datanotfound, Toast.LENGTH_LONG).show()
                    }
                    is MainState.CliamBusiness -> {
                        Log.e("testtt", it.cliamBusinessResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = CliamBusinessAdapter(
                            it.cliamBusinessResp!!.data,
                            this@CliamBusinessActivity
                        )
                        binding.cliambusinessRecyclerview.adapter = adapter
                        homeRenderList(it.cliamBusinessResp)

                    }

                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(cliamBusinessResp: CliamBusinessResp) {
        if (cliamBusinessResp!!.data.isNotEmpty()) {
            binding.cliambusinessRecyclerview.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.cliambusinessRecyclerview.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.CliamBusiness(
                    "4"
                )

            )
        }

    }

    private fun setupUI() {
        admin_id = intent.getStringExtra("admin_id").toString()
        binding.cliambusinessRecyclerview.addItemDecoration(
            DividerItemDecoration(
                this@CliamBusinessActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.cliambusinessRecyclerview.layoutManager =
            LinearLayoutManager(this@CliamBusinessActivity)
        prefHelper = PrefHelper(this)

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}