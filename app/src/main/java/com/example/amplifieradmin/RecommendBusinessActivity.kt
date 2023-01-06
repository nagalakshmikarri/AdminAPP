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
import com.example.amplifieradmin.data.model.RecommmendBusinnessResp
import com.example.amplifieradmin.databinding.ActivityCliamBusinessBinding
import com.example.amplifieradmin.databinding.ActivityRecommendBusinessBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.CliamBusinessAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommendBusinessAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecommendBusinessActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityRecommendBusinessBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: RecommendBusinessAdapter
    private val binding get() = _binding!!
    private var admin_id = ""
    private var s_id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRecommendBusinessBinding.inflate(layoutInflater)
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
                    is MainState.RecommendBusiness -> {
                        Log.e("testtt", it.recommendBusinessResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = RecommendBusinessAdapter(
                            it.recommendBusinessResp!!.data,
                            this@RecommendBusinessActivity
                        )
                        binding.recyclerView.adapter = adapter
                        homeRenderList(it.recommendBusinessResp)

                    }

                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(recommendBusinessResp: RecommmendBusinnessResp) {
        if (recommendBusinessResp!!.data.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)


    }

    private fun setupUI() {
        admin_id = intent.getStringExtra("admin_id").toString()
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@RecommendBusinessActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this@RecommendBusinessActivity)
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.RecommendBusiness

            )
        }
    }
}
