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
import com.example.amplifieradmin.data.model.AddSubTypeInviteReq
import com.example.amplifieradmin.data.model.SubTypeInviteListReq
import com.example.amplifieradmin.data.model.SubTypeInviteListResp
import com.example.amplifieradmin.databinding.ActivityCliamUsersListBinding
import com.example.amplifieradmin.databinding.ActivityPartiesBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.CliamDetailAdaper
import com.example.amplifieradmin.ui.main.Adapter.PartiesAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommandedServicesAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class PartiesActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityPartiesBinding? = null
    lateinit var prefHelper: PrefHelper
    private val binding get() = _binding!!
    private var type: String? = null
    private lateinit var adapter: PartiesAdapter
    private var type_id=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPartiesBinding.inflate(layoutInflater)
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
                    is MainState.SubInviteType -> {
                        Log.e("testtt", it.subTypeInviteListResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = PartiesAdapter(
                            it.subTypeInviteListResp!!.list,
                            this@PartiesActivity,
                            object :PartiesAdapter.OnItemClick{
                                override fun onItemClick(
                                    type: String,
                                    id: String
                                ) {
                                    val intent = Intent(this@PartiesActivity, GetCategoriesActivity::class.java)
                                    intent.putExtra("type", type)
                                    intent.putExtra("id", id)
                                    startActivity(intent);
                                }

                            }
                        )
                        binding.servicesRecy.adapter = adapter
                        homeRenderList(it.subTypeInviteListResp)
                    }


                    is MainState.AddSubTypeInvite -> {
                        Log.e("testtt", it.addSubTypeInviteResp?.status.toString())
                        binding.progressBar.visibility = View.GONE


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

    private fun homeRenderList(subTypeInviteListResp: SubTypeInviteListResp) {
        if (subTypeInviteListResp!!.list.isNotEmpty()) {
            binding.servicesRecy.visibility = View.VISIBLE
        } else {
            binding.servicesRecy.visibility = View.GONE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

        val subTypeInviteListReq=SubTypeInviteListReq(type_id)

        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.SubInviteType(
                    subTypeInviteListReq
                )

            )
        }

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupUI() {
        type = intent.getStringExtra("type").toString()
        binding.partiesTv.text = type
        type_id=intent.getStringExtra("id").toString()

        binding.servicesRecy.addItemDecoration(
            DividerItemDecoration(
                this@PartiesActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.servicesRecy.layoutManager =
            LinearLayoutManager(this@PartiesActivity)


        val addSubTypeInviteReq= AddSubTypeInviteReq(binding.categoryEt.text.toString(),type_id)

        binding.saveButton.setOnClickListener {
            if (checkValidations()) {
                hideKeyboard(currentFocus ?: View(this))
                lifecycleScope.launch {
                    homeViewModel.homeIntent.send(
                        MainIntent.AddSubTypeInvite(
                            addSubTypeInviteReq                        )
                    )

                }
            }

        }

    }

    private fun checkValidations(): Boolean {


        if (binding.categoryEt.text.toString().trim().isEmpty()) {
            showMessage("Please Enter Sub Types")
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