package com.example.amplifieradmin.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.BlockDetailsActivity
import com.example.amplifieradmin.ConfirmDetailsActivity
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.*
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.FragmentBlockListBinding
import com.example.amplifieradmin.databinding.FragmentConfirmListBinding
import com.example.amplifieradmin.ui.main.Adapter.BlockedListAdapter
import com.example.amplifieradmin.ui.main.Adapter.ConfirmListAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch


class BlockListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: BlockedListAdapter
    private var _binding: FragmentBlockListBinding? = null
    private val binding get() = _binding!!
    private var s_id=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlockListBinding.inflate(inflater, container, false)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
        return binding.root
    }

    private fun setupClicks() {

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
                    is MainState.BlockedList -> {
                        Log.e("testtt", it.blockedListResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = BlockedListAdapter(
                            it.blockedListResp!!.data,
                            requireActivity(),
                            object :BlockedListAdapter.OnItemClick{
                                override fun onItemClick(
                                    s_id: String
                                ) {
                                    confirmClicks(s_id)
                                }

                            },
                            object :BlockedListAdapter.OnPhoneClick{
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

                            },
                            object :BlockedListAdapter.OnBlockClick{
                                override fun onBlockClick(
                                    item: BlockedListRespData
                                ) {
                                    var intent = Intent(requireActivity(), BlockDetailsActivity::class.java)
                                    intent.putExtra("info", item)
                                    startActivity(intent)

                                }

                            }
                        )
                        binding.blockListRecy.adapter = adapter
                        homeRenderList(it.blockedListResp)

                    }

                    is MainState.ConfirmUser->{
                        Log.e("testtt", "Succesfully Confirmed the business")
                        binding.progressBar.visibility = View.GONE

                        binding.blockListRecy.visibility = View.GONE
                        binding.noResultTv.visibility = View.VISIBLE
                        val blockedListReq= BlockedListReq(s_id)

                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.BlockedList(
                                    blockedListReq
                                )

                            )
                        }

                    }

                    else -> {}
                }
            }
        }

    }
/*
    private fun dialCall(listingData: ListingData) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + listingData.sPhone))
        startActivity(intent)
    }
*/

    private fun confirmClicks(sId: String) {
        val dialog = Dialog(requireContext())
        val inflaterBlock =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bindingDialog = AlertDialogBinding.inflate(inflaterBlock)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bindingDialog.root)



        bindingDialog.tvSubTitle.text = "Are you sure do you want to Confirm?"
        bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
        bindingDialog.cancelIv.setOnClickListener { dialog.dismiss() }


        val confirmUserReq= ConfirmUserReq(sId)

        bindingDialog.btnYes.setOnClickListener {
            lifecycleScope.launch {
                homeViewModel.homeIntent.send(
                    MainIntent.ConfirmUser(
                        confirmUserReq
                    )

                )
            }

            dialog.dismiss()
        }

        dialog.show()

    }

    private fun homeRenderList(blockedListResp: BlockedListResp) {
        if (blockedListResp!!.data.isNotEmpty()) {
            binding.blockListRecy.visibility = View.VISIBLE
            binding.noResultTv.visibility = View.GONE
        } else {
            binding.blockListRecy.visibility = View.GONE
            binding.noResultTv.visibility = View.VISIBLE
        }
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

        binding.blockListRecy.visibility = View.GONE
        binding.noResultTv.visibility = View.VISIBLE
        val blockedListReq= BlockedListReq(s_id)

        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.BlockedList(
                    blockedListReq
                )

            )
        }


    }

    private fun setupUI() {
        binding.blockListRecy.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.blockListRecy.layoutManager =
            LinearLayoutManager(requireActivity())
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BlockListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}