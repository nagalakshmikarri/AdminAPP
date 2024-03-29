package com.example.amplifieradmin.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.BlockUserJobReq
import com.example.amplifieradmin.data.model.BlockUserReq
import com.example.amplifieradmin.data.model.ConfirmListReq
import com.example.amplifieradmin.data.model.ConfirmedJobsResp
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.FragmentAllJobsBinding
import com.example.amplifieradmin.databinding.FragmentConfirmJobsBinding
import com.example.amplifieradmin.ui.main.Adapter.AllJobAdapter
import com.example.amplifieradmin.ui.main.Adapter.ConfirmJobsAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch


class ConfirmJobsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: ConfirmJobsAdapter
    private var _binding: FragmentConfirmJobsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmJobsBinding.inflate(inflater, container, false)
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
                    is MainState.ConfirmJobs -> {
                        Log.e("testtt", it.confirmedJobsResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = ConfirmJobsAdapter(
                            it.confirmedJobsResp!!.data,
                            requireActivity(),
                            object :ConfirmJobsAdapter.OnBlockClick{
                                override fun onBlockClick(
                                    id: String
                                ) {
                                    blockedClicks(id)
                                }

                            }
                        )
                        binding.confirmJobsRecy.adapter = adapter
                        homeRenderList(it.confirmedJobsResp)

                    }

                    is MainState.BlockUserJobs->{
                        Log.e("testtt", "Succesfully blocked the job")
                        binding.progressBar.visibility = View.GONE

                        binding.confirmJobsRecy.visibility = View.GONE
                        binding.noResultTv.visibility = View.VISIBLE

                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.ConfirmJobs

                            )
                        }

                    }



                    else -> {}
                }
            }
        }

    }

    private fun blockedClicks(id: String) {
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


        val blockUserJobReq= BlockUserJobReq(id)

        bindingDialog.btnYes.setOnClickListener {
            lifecycleScope.launch {
                homeViewModel.homeIntent.send(
                    MainIntent.BlockUserJobs(
                        blockUserJobReq
                    )

                )
            }


            dialog.dismiss()
        }

        dialog.show()

    }

    private fun homeRenderList(confirmedJobsResp: ConfirmedJobsResp) {
        if (confirmedJobsResp!!.data.isNotEmpty()) {
            binding.confirmJobsRecy.visibility = View.VISIBLE
            binding.noResultTv.visibility = View.GONE
        } else {
            binding.confirmJobsRecy.visibility = View.GONE
            binding.noResultTv.visibility = View.VISIBLE
        }
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

    }

    private fun setupUI() {
        binding.confirmJobsRecy.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.confirmJobsRecy.layoutManager =
            LinearLayoutManager(requireActivity())


    }
    override fun onResume() {
        super.onResume()
        binding.confirmJobsRecy.visibility = View.GONE
        binding.noResultTv.visibility = View.VISIBLE

        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.ConfirmJobs

            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConfirmJobsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}