package com.example.amplifieradmin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.databinding.ActivityAdsListStatusBinding
import com.example.amplifieradmin.databinding.ActivityPendingBinding
import com.example.amplifieradmin.databinding.DialogLogoutBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.AdminUserAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class AdsListStatusActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityAdsListStatusBinding? = null
    private val binding get() = _binding!!
    private var subadmin_id = ""
    private var pushToken: String = ""
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAdsListStatusBinding.inflate(layoutInflater)
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

                    is MainState.Updatedevice -> {
                        Log.e("UpdateDevice", "Success: " + it.updateDeviceResp.toString())


                    }

                    is MainState.SubAdminInfo -> {
                        Log.e("testtt", it.subAdminInfoResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        binding.tvSubAdminName.text = it.subAdminInfoResp.data.admin_name
                        binding.tvPhoneNumber.text = it.subAdminInfoResp.data.admin_phone
                        binding.tvAcceptCount.text = it.subAdminInfoResp.accepted.toString()
                        binding.tvRejectCount.text = it.subAdminInfoResp.rejected.toString()
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

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.SubAdminInfo(subadmin_id.toInt())

            )
        }
        try {
            val isFirebase = FirebaseApp.initializeApp(this@AdsListStatusActivity)
            // Log.d("FIRE BASE", "  : " + isFirebase);
            if (isFirebase != null) {
                val messaging = FirebaseMessaging.getInstance()
                Log.e("tokenFCM", messaging.token.toString())
                // Log.d("FCM Token", "  : " + fcmToken);
            }


            val messaging = FirebaseMessaging.getInstance()
            messaging.token.addOnSuccessListener { s: String ->
                Log.d("ON_TOKEN", s)
                pushToken = s
                val params = JSONObject()
                val info = JSONObject()
                try {
                    info.put("os", "android")
                    info.put("framework", "flutter")
                    info.put("cihaz_bilgisi", JSONObject())
                    params.put("token", pushToken)
                    params.put("device_info", info)
                    lifecycleScope.launch {
                        homeViewModel.homeIntent.send(
                            MainIntent.UpdateDevice(
                                prefHelper.getString(com.example.amplifieradmin.helper.Constants.PREF_ADMINID),
                                pushToken,
                            )

                        )
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            Log.e("FIRE BASE", "  : $e")
        }


    }

    private fun setupUI() {
//        Toast.makeText(this, intent.getStringExtra("subadmin_id").toString(), Toast.LENGTH_SHORT).show()
        subadmin_id = intent.getStringExtra("subadmin_id").toString()

        prefHelper = PrefHelper(this)
        Log.e("SubAdminId", prefHelper.getString(Constants.PREF_ADMINID)!!)

    }

    private fun setupClicks() {


        binding.logout.setOnClickListener {

            val dialog = Dialog(this)
            val inflaterBlock =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bindingBlock = DialogLogoutBinding.inflate(inflaterBlock)
            dialog.setContentView(bindingBlock.root)
            dialog.show()
            bindingBlock.btnYes.setOnClickListener {
                prefHelper.clear()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            bindingBlock.btnNo.setOnClickListener {
                dialog.cancel()
            }

        }

        binding.tvPendingList.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, PendingActivity::class.java)
            intent.putExtra("subadmin_id", subadmin_id)
            startActivity(intent);
        }


        binding.referBusiness.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, RecommendBusinessActivity::class.java)
            startActivity(intent);
        }
        binding.category1.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, CategoryActivity::class.java)
            startActivity(intent);
        }

        binding.businessListTv.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, AllBusinessListActivity::class.java)
            startActivity(intent);
        }

        binding.tvAceeptads.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, AcceptAdsActivity::class.java)
            startActivity(intent);
        }
        binding.AdsRejected.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, RejectAcivity::class.java)
            startActivity(intent);
        }

        binding.BusinessList.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, BusinessListActivity::class.java)
            startActivity(intent);
        }

        binding.CliamBusiness.setOnClickListener {
            val intent = Intent(this@AdsListStatusActivity, CliamBusinessListActivity::class.java)
            startActivity(intent);
        }
    }
}