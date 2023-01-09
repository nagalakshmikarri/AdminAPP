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
import com.example.amplifieradmin.data.model.AllBusinessListResp
import com.example.amplifieradmin.databinding.ActivityAllBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityRecommendBusinessBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AllBusinessListAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommendBusinessAdapter
import com.example.amplifieradmin.ui.main.Adapter.TagsSpinnerAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllBusinessListActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityAllBusinessListBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: AllBusinessListAdapter
    private val binding get() = _binding!!
    private var admin_id = ""
    private var s_id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAllBusinessListBinding.inflate(layoutInflater)
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
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Error -> {
                        Log.e("testtt", "Not found/Error")
                        binding.progressBar.visibility = View.GONE
                        //Toast.makeText(context, R.string.datanotfound, Toast.LENGTH_LONG).show()
                    }
                    is MainState.All_business -> {
                        Log.e("testtt", it.allBusinessListResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = AllBusinessListAdapter(
                            it.allBusinessListResp!!.data,
                            this@AllBusinessListActivity
                        )
                        binding.listRecyclerView.adapter = adapter
                        homeRenderList(it.allBusinessListResp)

                    }

                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(allBusinessListResp: AllBusinessListResp) {
        if (allBusinessListResp!!.data.isNotEmpty()) {
            binding.listRecyclerView.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.listRecyclerView.visibility = View.GONE
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
        binding.listRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this@AllBusinessListActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.listRecyclerView.layoutManager =
            LinearLayoutManager(this@AllBusinessListActivity)
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
                MainIntent.AllBusinessList

            )
        }
    }

}