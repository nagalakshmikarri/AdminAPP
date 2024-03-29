package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.AllBusinessListData
import com.example.amplifieradmin.data.model.AllBusinessListResp
import com.example.amplifieradmin.data.model.GetCategoryResp
import com.example.amplifieradmin.data.model.GetCategoryRespData
import com.example.amplifieradmin.databinding.ActivityAdvertaisementsBinding
import com.example.amplifieradmin.databinding.ActivityBusinessCategoryBinding
import com.example.amplifieradmin.ui.main.Adapter.BusinessCategoryAdapter
import com.example.amplifieradmin.ui.main.Adapter.CategoryAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BusinessCategoryActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityBusinessCategoryBinding? = null
    private val binding get() = _binding!!
    private var s_id = ""
    private lateinit var adapter: BusinessCategoryAdapter
    var getCategoryResp: GetCategoryResp? = null
    var filteredDataList: GetCategoryResp? = null
    private lateinit var getCatData: GetCategoryRespData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBusinessCategoryBinding.inflate(layoutInflater)
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

                    is MainState.business_Category -> {
                        Log.e("testtt", it.businessCategoryResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        finish()


                    }
                    is MainState.Get_Category -> {
                        Log.e("testsssstt", it.getCategoryResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        getCategoryResp = it.getCategoryResp
                        binding.searchBarText.text.clear()
                        if (it.getCategoryResp.list.isNotEmpty()) {
                            binding.businessCategory.visibility = View.VISIBLE
                            binding.noTextTv.visibility = View.GONE
                        } else {
                            binding.businessCategory.visibility = View.GONE
                            binding.noTextTv.visibility = View.VISIBLE
                        }

                        adapter = BusinessCategoryAdapter(it.getCategoryResp.list,this@BusinessCategoryActivity,
                            object : BusinessCategoryAdapter.OnItemClick {
                                override fun onClick(
                                    data: GetCategoryRespData
                                ) {
                                    getCatData = data
                                }
                            }

                        )
                        filteredDataList=it.getCategoryResp
                        binding.businessCategory.adapter = adapter
                        homeRenderList(it.getCategoryResp!!.list)

                    }
                    is MainState.Error -> {
                        Log.e("testtt", "Error")
                        binding.progressBar.visibility = View.GONE
                        /* Toast.makeText(this@CatlogProductesActivity, it.error, Toast.LENGTH_LONG)
                             .show()*/
                    }
                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(list: List<GetCategoryRespData>) {

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.GetCategory
            )
        }

    }

    private fun setupUI() {
        s_id = intent.getStringExtra("s_id").toString()
        binding.businessCategory.addItemDecoration(
            DividerItemDecoration(
                this@BusinessCategoryActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.businessCategory.layoutManager = LinearLayoutManager(this@BusinessCategoryActivity)

    }

    private fun setupClicks() {
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.searchBarText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                filter(s.toString())
                Log.e("TAG", "filter1: " + s)

            }
        })

        binding.tvSave.setOnClickListener {
            lifecycleScope.launch {
                homeViewModel.homeIntent.send(
                    MainIntent.BusinessCategory(s_id,getCatData.id)

                )
            }
        }
    }
    private fun filter(text: String?) {
        val filteredHomeList = mutableListOf<GetCategoryRespData>()

        Log.e("TAG", "filter: " + text)
        if (filteredDataList != null) {
            for (filteredList in filteredDataList!!.list) {
                if (filteredList.name.toLowerCase().contains(text?.toLowerCase().toString())) {
                    filteredHomeList.add(filteredList)
                }
            }
            adapter?.filterList(filteredHomeList)
        }

    }

}