package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.model.CliamDetailRespData
import com.example.amplifieradmin.databinding.ActivityCliamBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityUserDetailsBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.viewmodel.HomeViewModel

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityUserDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var prefHelper: PrefHelper
    private var cliamDetailRespData: CliamDetailRespData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
        setupUI()
//        setupViewModel()
//        observeViewModel()
    }

    private fun setupUI() {
        prefHelper = PrefHelper(this)
        cliamDetailRespData = intent.getParcelableExtra("user_details")

        //UI
        binding.BusinessTv.text = cliamDetailRespData?.s_business
        binding.userNameTv.text = cliamDetailRespData?.s_username
        binding.tvPhone.text = cliamDetailRespData?.s_phone
        binding.tvEmail.text = cliamDetailRespData?.s_email

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}