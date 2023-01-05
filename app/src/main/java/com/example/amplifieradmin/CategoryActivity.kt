package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.GetCategoryResp
import com.example.amplifieradmin.data.model.GetCategoryRespData
import com.example.amplifieradmin.databinding.ActivityAdvertaisementsBinding
import com.example.amplifieradmin.databinding.ActivityCategoryBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AdsPendingAdapter
import com.example.amplifieradmin.ui.main.Adapter.CategoryAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommendBusinessAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import com.google.android.libraries.places.internal.it
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    private var subAdmin_id = ""
    var getCategoryResp: GetCategoryResp? = null
    lateinit var prefHelper: PrefHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryBinding.inflate(layoutInflater)
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

                    is MainState.Category -> {
                        Log.e("testtt", it.categoryResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.GetCategory
                            )

                        }


                    }
                    is MainState.Get_Category -> {
                        Log.e("testsssstt", it.getCategoryResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        getCategoryResp = it.getCategoryResp
                        binding.servicesEt.text.clear()
                        if (it.getCategoryResp.list.isNotEmpty()) {
                            binding.servicessRecyc.visibility = View.VISIBLE
                            binding.noTextTv.visibility = View.GONE
                        } else {
                            binding.servicessRecyc.visibility = View.GONE
                            binding.noTextTv.visibility = View.VISIBLE
                        }

                        adapter = CategoryAdapter(it.getCategoryResp.list,this@CategoryActivity,
                           )
                        binding.servicessRecyc.adapter = adapter
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
        if (list!!.isNotEmpty()) {
            binding.servicessRecyc.visibility = View.VISIBLE
            binding.noTextTv.visibility = View.GONE
        } else {
            binding.servicessRecyc.visibility = View.GONE
            binding.noTextTv.visibility = View.VISIBLE
        }
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
        prefHelper=PrefHelper(this)
        binding.servicessRecyc.addItemDecoration(
            DividerItemDecoration(
                this@CategoryActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.servicessRecyc.layoutManager = LinearLayoutManager(this@CategoryActivity)
    }
    private fun setupClicks() {
        binding.servicesEt.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.servicesEt.text.toString() != "") {
                    lifecycleScope.launch {
                        homeViewModel.homeIntent.send(
                            MainIntent.Category(
                               // prefHelper.getString(Constants.PREF_ADMINID)!!,
                                binding.servicesEt.text.toString()
                            )
                        )

                    }
                } else {

                }
            }
            false
        })


    }
}