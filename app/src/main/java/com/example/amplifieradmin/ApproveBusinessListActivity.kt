package com.example.amplifieradmin

import android.content.Intent
import android.net.Uri
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
import com.example.amplifieradmin.data.model.ApprovedBusinessListResp
import com.example.amplifieradmin.data.model.CliamBusinessListResp
import com.example.amplifieradmin.data.model.CliamBusinessListRespData
import com.example.amplifieradmin.databinding.ActivityApproveBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityCliamBusinessListBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.ApproveBusinessListAdapter
import com.example.amplifieradmin.ui.main.Adapter.BlockedListAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class ApproveBusinessListActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityApproveBusinessListBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: ApproveBusinessListAdapter
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityApproveBusinessListBinding.inflate(layoutInflater)
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
                    is MainState.UserApprovedBusiness -> {
                        Log.e("testtt", it.approvedBusinessListResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = ApproveBusinessListAdapter(
                            it.approvedBusinessListResp!!.data,
                            this@ApproveBusinessListActivity,
                            object :ApproveBusinessListAdapter.OnItemClick{
                                override fun onItemClick(
                                    s_business: String,
                                    s_id: String,
                                    cliamBusinessListRespData: CliamBusinessListRespData
                                ) {
                                    val intent =
                                        Intent(
                                            this@ApproveBusinessListActivity,
                                            CliamUsersListActivity::class.java
                                        )
                                    intent.putExtra("s_id", s_id)
                                    intent.putExtra("s_business", s_business)
                                    intent.putExtra("s_phone", cliamBusinessListRespData.s_phone)
                                    startActivity(intent);

                                }

                            },
                            object : ApproveBusinessListAdapter.OnPhoneClick{
                                override fun onPhoneClick(
                                    phone: String
                                ) {
                                    val intent = Intent(
                                        Intent.ACTION_DIAL, Uri.parse(
                                            "tel:" +phone
                                        )
                                    )
                                    startActivity(intent)


                                }

                            }
                        )
                        binding.cliambusinessRecyclerview.adapter = adapter
                        homeRenderList(it.approvedBusinessListResp)

                    }

                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(cliamBusinessListResp: ApprovedBusinessListResp) {
        if (cliamBusinessListResp.data.isNotEmpty()) {
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
                MainIntent.UserApprovedBusiness

            )
        }
    }

    private fun setupUI() {

        binding.cliambusinessRecyclerview.addItemDecoration(
            DividerItemDecoration(
                this@ApproveBusinessListActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.cliambusinessRecyclerview.layoutManager =
            LinearLayoutManager(this@ApproveBusinessListActivity)
        prefHelper = PrefHelper(this)

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}