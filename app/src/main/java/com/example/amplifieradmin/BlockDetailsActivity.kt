package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amplifieradmin.data.model.BlockedListRespData
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.databinding.ActivityBlockDetailsBinding
import com.example.amplifieradmin.databinding.ActivityBusinessInfoBinding

class BlockDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityBlockDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBlockDetailsBinding.inflate(layoutInflater)
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
        val dataDetails: BlockedListRespData?=intent.getParcelableExtra<BlockedListRespData>("info")
        setData(dataDetails)

    }

    private fun setData(blockedListRespData: BlockedListRespData?) {
        binding.bussinessTv.text=blockedListRespData?.s_business
        binding.categoryTv.text=blockedListRespData?.businesscategory
        binding.listingTv.text=blockedListRespData?.listing_type
        binding.phoneNumber.text=blockedListRespData?.s_phone_code+blockedListRespData?.s_phone
        binding.locatedTv.text=blockedListRespData?.locatedin
        binding.streetTv.text=blockedListRespData?.s_address
        binding.areanameTv.text=blockedListRespData?.s_address2
        binding.stateTv.text=blockedListRespData?.s_address1
        binding.cityTv.text=blockedListRespData?.city
        binding.pincodeTv.text=blockedListRespData?.s_address3


    }
}