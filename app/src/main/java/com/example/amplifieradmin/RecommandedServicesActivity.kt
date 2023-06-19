package com.example.amplifieradmin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.AddInviteTypeReq
import com.example.amplifieradmin.data.model.ListInviteTypeResp
import com.example.amplifieradmin.databinding.ActivityRecommandedServicesBinding
import com.example.amplifieradmin.databinding.ActivityRecommendBusinessBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.CliamBusinessAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommandedServicesAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommendBusinessAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class RecommandedServicesActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityRecommandedServicesBinding? = null
    lateinit var prefHelper: PrefHelper
    private lateinit var adapter: RecommandedServicesAdapter
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRecommandedServicesBinding.inflate(layoutInflater)
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
                    is MainState.ListingInviteTpe -> {
                        Log.e("testtt", it.listInviiteTypeResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = RecommandedServicesAdapter(
                            it.listInviiteTypeResp!!.list,
                            this@RecommandedServicesActivity,
                            object :RecommandedServicesAdapter.OnItemClick{
                                override fun onItemClick(
                                    type: String,
                                    cat_id: String
                                ) {
                                    val intent = Intent(this@RecommandedServicesActivity, PartiesActivity::class.java)
                                    intent.putExtra("type", type)
                                    intent.putExtra("cat_id", cat_id)

                                    startActivity(intent);

                                }

                            }
                        )
                        binding.servicesRecy.adapter = adapter
                        homeRenderList(it.listInviiteTypeResp)
                    }

                    is MainState.AddInviteType -> {
                        Log.e("testtt", it.addInviteTypeResp?.status.toString())
                        binding.progressBar.visibility = View.GONE

                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.ListingInviteType

                            )
                        }


                    }


                    else -> {}
                }
            }
        }

    }
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun homeRenderList(listInviiteTypeResp: ListInviteTypeResp) {
        if (listInviiteTypeResp!!.list.isNotEmpty()) {
            binding.servicesRecy.visibility = View.VISIBLE
        } else {
            binding.servicesRecy.visibility = View.GONE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.ListingInviteType

            )
        }

    }

    private fun setupUI() {
        binding.servicesRecy.addItemDecoration(
            DividerItemDecoration(
                this@RecommandedServicesActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.servicesRecy.layoutManager =
            LinearLayoutManager(this@RecommandedServicesActivity)

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }


        binding.saveButton.setOnClickListener {

            if (checkValidations()) {
                Log.e("TAG", "setupClicks: ${binding.categoryEt.text.toString()}", )
                val addInviteTypeReq=AddInviteTypeReq(binding.categoryEt.text.toString())

                hideKeyboard(currentFocus ?: View(this))
                lifecycleScope.launch {
                    homeViewModel.homeIntent.send(
                        MainIntent.AddInviteType(
                            addInviteTypeReq
                        )
                    )

                }
            }

        }

    }

    private fun checkValidations(): Boolean {


        if (binding.categoryEt.text.toString().trim().isEmpty()) {
            showMessage("Please Enter Add Types")
            binding.categoryEt.background = resources.getDrawable(R.drawable.border_red)
            binding.categoryEt.requestFocus()
            return false
        } else {
            binding.categoryEt.background = resources.getDrawable(R.drawable.border)
        }

        return true

    }

    private fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

}