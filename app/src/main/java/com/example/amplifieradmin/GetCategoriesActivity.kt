package com.example.amplifieradmin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.*
import com.example.amplifieradmin.databinding.ActivityGetCategoriesBinding
import com.example.amplifieradmin.databinding.ActivityPartiesBinding
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.SaveAlertDialogBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.CategoriiesAdapter
import com.example.amplifieradmin.ui.main.Adapter.PartiesAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class GetCategoriesActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityGetCategoriesBinding? = null
    lateinit var prefHelper: PrefHelper
    private val binding get() = _binding!!
    private var type: String? = null
    private var subtype_id: String? = null
    private var cat_id: String? = null
    private lateinit var adapter: CategoriiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGetCategoriesBinding.inflate(layoutInflater)
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
                    is MainState.GetCategories -> {
                        Log.e("testtt", it.getCategoriesResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = CategoriiesAdapter(
                            it.getCategoriesResp!!.list,
                            this@GetCategoriesActivity,
                        )

                        binding.categoriesRecyclerView.adapter = adapter
                        homeRenderList(it.getCategoriesResp)
                    }


                    is MainState.SubCategories -> {
                        Log.e("testtt", it.subCategoriesResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        saveClick()

                    }
                    is MainState.EditSubTypeCategory -> {
                        Log.e("testtt", it.editSubTypeCategoryResp?.status.toString())
                        binding.progressBar.visibility = View.GONE

                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.GetCategories

                            )
                        }


                    }


                    else -> {}
                }
            }
        }

    }

    private fun saveClick() {
        val dialog = Dialog(this)
        val inflaterBlock =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bindingDialog = SaveAlertDialogBinding.inflate(inflaterBlock)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bindingDialog.root)



        bindingDialog.tvSubTitle.text = "Categories Updated"
        bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }





        bindingDialog.btnCancel.setOnClickListener {
            finish()
            dialog.dismiss()
        }

        dialog.show()


    }

    private fun homeRenderList(categoriesResp: GetCategoriesResp) {
        if (categoriesResp!!.list.isNotEmpty()) {
            binding.categoriesRecyclerView.visibility = View.VISIBLE
        } else {
            binding.categoriesRecyclerView.visibility = View.GONE
        }
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)



        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.GetCategories

            )
        }


    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.saveTv.setOnClickListener {

            val subCategoriesReq = SubCategoriesReq(subtype_id!!, cat_id!!)

            lifecycleScope.launch {
                homeViewModel.homeIntent.send(
                    MainIntent.SubCategories(
                        subCategoriesReq
                    )

                )
            }
        }
    }


    private fun setupUI() {
        type = intent.getStringExtra("type").toString()
        binding.categoriesTv.text = type

        subtype_id = intent.getStringExtra("id").toString()
        cat_id = intent.getStringExtra("id").toString()

        binding.categoriesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this@GetCategoriesActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this@GetCategoriesActivity)


    }
}