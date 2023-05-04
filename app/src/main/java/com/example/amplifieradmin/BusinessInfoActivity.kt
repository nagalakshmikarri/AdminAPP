package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.databinding.ActivityBusinessInfoBinding

class BusinessInfoActivity : AppCompatActivity() {
    private var _binding: ActivityBusinessInfoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBusinessInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI();
        setupClicks()
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupUI() {
        val dataDetails:RecommendBusinessData?=intent.getParcelableExtra<RecommendBusinessData>("info")
        setData(dataDetails)
    }

    private fun setData(recommendBusinessData: RecommendBusinessData?) {
        binding.bussinessTv.text=recommendBusinessData?.s_business
        binding.categoryTv.text=recommendBusinessData?.businesscategory
        binding.listingTv.text=recommendBusinessData?.listing_type
        binding.phoneNumber.text=recommendBusinessData?.s_phone_code+recommendBusinessData?.s_phone
        binding.locatedTv.text=recommendBusinessData?.locatedin
        binding.streetTv.text=recommendBusinessData?.s_address
        binding.areanameTv.text=recommendBusinessData?.s_address2
        binding.stateTv.text=recommendBusinessData?.s_state
        binding.countryTv.text=recommendBusinessData?.s_country
        binding.cityTv.text=recommendBusinessData?.city
        binding.pincodeTv.text=recommendBusinessData?.s_address3

    }

}