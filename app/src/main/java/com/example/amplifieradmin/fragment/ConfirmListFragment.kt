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
import com.example.amplifieradmin.BusinessInfoActivity
import com.example.amplifieradmin.ConfirmDetailsActivity
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.BlockUserReq
import com.example.amplifieradmin.data.model.ConfirmListReq
import com.example.amplifieradmin.data.model.ConfirmedListResp
import com.example.amplifieradmin.data.model.ConfirmedListRespData
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.FragmentConfirmListBinding
import com.example.amplifieradmin.databinding.FragmentReferBusinessListBinding
import com.example.amplifieradmin.ui.main.Adapter.ConfirmListAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommendBusinessAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch

class ConfirmListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: ConfirmListAdapter
    private var _binding: FragmentConfirmListBinding? = null
    private val binding get() = _binding!!
    private var s_id=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmListBinding.inflate(inflater, container, false)
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
                    is MainState.ConfirmList -> {
                        Log.e("testtt", it.confirmedListResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = ConfirmListAdapter(
                            it.confirmedListResp!!.data,
                            requireActivity(),
                            object: ConfirmListAdapter.OnItemClick{
                                override fun onItemClick(
                                    s_id: String
                                ) {
                                    blockedClickss(s_id)
                                }

                            },
                            object :ConfirmListAdapter.OnPhoneClick{
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
                            object:ConfirmListAdapter.OnConfirmClick{
                                override fun onConfirmClick(
                                    item: ConfirmedListRespData
                                ) {
                                    var intent = Intent(requireActivity(), ConfirmDetailsActivity::class.java)
                                    intent.putExtra("info", item)
                                    startActivity(intent)

                                }

                            }
                        )
                        binding.confirmListRecy.adapter = adapter
                        homeRenderList(it.confirmedListResp)

                    }

                    is MainState.BlockedUser->{
                        Log.e("testtt", "Succesfully blocked the business")
                        binding.progressBar.visibility = View.GONE

                        val confirmListReq=ConfirmListReq(s_id)

                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.ConfirmList(
                                    confirmListReq
                                )

                            )
                        }
                    }
                    else -> {}
                }
            }
        }

    }


    private fun blockedClickss(s_id: String) {
        val dialog = Dialog(requireContext())
        val inflaterBlock =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bindingDialog = AlertDialogBinding.inflate(inflaterBlock)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bindingDialog.root)



        bindingDialog.tvSubTitle.text = "Are you sure do you want to Block?"
        bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
        bindingDialog.cancelIv.setOnClickListener { dialog.dismiss() }


        val blockUserReq=BlockUserReq(s_id)

        bindingDialog.btnYes.setOnClickListener {
            lifecycleScope.launch {
                homeViewModel.homeIntent.send(
                    MainIntent.BlockeUser(
                        blockUserReq
                    )

                )
            }


            dialog.dismiss()
        }

        dialog.show()

    }

    private fun homeRenderList(confirmedListResp: ConfirmedListResp) {
        if (confirmedListResp!!.data.isNotEmpty()) {
            binding.confirmListRecy.visibility = View.VISIBLE
            binding.tvEmptyMsg.visibility = View.GONE
        } else {
            binding.confirmListRecy.visibility = View.GONE
            binding.tvEmptyMsg.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

        val confirmListReq=ConfirmListReq(s_id)

        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.ConfirmList(
                    confirmListReq
                )

            )
        }

    }

    private fun setupUI() {
        binding.confirmListRecy.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.confirmListRecy.layoutManager =
            LinearLayoutManager(requireActivity())

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConfirmListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}