package com.example.amplifieradmin.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.BlockUserReq
import com.example.amplifieradmin.data.model.ConfirmUserReq
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.data.model.RecommmendBusinnessResp
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.FragmentReferBusinessListBinding
import com.example.amplifieradmin.ui.main.Adapter.ConfirmListAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommendBusinessAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch


class ReferBusinessListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: RecommendBusinessAdapter
    private var _binding: FragmentReferBusinessListBinding? = null
    private val binding get() = _binding!!
    private var admin_id = ""
    private var s_id=""
    var filterList: RecommmendBusinnessResp? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReferBusinessListBinding.inflate(inflater, container, false)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
        return binding.root
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
                    is MainState.RecommendBusiness -> {
                        binding.recyclerView.visibility = View.GONE
                        binding.noResultTv.visibility = View.VISIBLE
                        Log.e("testtt", it.recommendBusinessResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = RecommendBusinessAdapter(
                            it.recommendBusinessResp!!.data,
                            requireActivity(),
                            object :RecommendBusinessAdapter.OnConfirmClick{
                                override fun onConfirmClick(
                                    s_id: String
                                ) {
                                    confirmClicks(s_id)
                                }

                            },
                            object :RecommendBusinessAdapter.OnBlockClick{
                                override fun onBlockClick(
                                    s_id: String
                                ) {
                                    blockClicks(s_id)
                                }

                            },
                            object:RecommendBusinessAdapter.OnItemClick{
                                override fun onItemClick(
                                    item: RecommendBusinessData
                                ) {
                                    var intent = Intent(requireActivity(), BusinessInfoActivity::class.java)
                                    intent.putExtra("info", item)
                                    startActivity(intent)

                                }

                            },
                            object : RecommendBusinessAdapter.OnPhoneClick{
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

                            }

                        )
                        binding.recyclerView.adapter = adapter
                        filterList = it.recommendBusinessResp
                        homeRenderList(it.recommendBusinessResp)

                    }

                    is MainState.ConfirmUser->{
                        binding.recyclerView.visibility = View.GONE
                        binding.noResultTv.visibility = View.VISIBLE
                        Log.e("testtt", "Succesfully Confirmed the business")
                        binding.progressBar.visibility = View.GONE
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.RecommendBusiness

                            )
                        }
                    }

                    is MainState.BlockedUser->{
                        binding.recyclerView.visibility = View.GONE
                        binding.noResultTv.visibility = View.VISIBLE
                        Log.e("testtt", "Succesfully blocked the business")
                        binding.progressBar.visibility = View.GONE
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.RecommendBusiness

                            )
                        }
                    }

                    else -> {}
                }
            }
        }

    }
    private fun filter(text: String?) {
        val filteredHomeList = mutableListOf<RecommendBusinessData>()
        if (filterList != null) {

            for (filteredList in filterList!!.data) {
                if (filteredList.s_business.toLowerCase().contains(text?.toLowerCase().toString())) {
                    filteredHomeList.add(filteredList)
                }
            }
            binding.recyclerView.adapter = adapter
            filteredHomeList.let { listOfUsers ->
                listOfUsers.let {
                    adapter.filterList(
                        it,
                    )
                }
            }
            adapter.notifyDataSetChanged()

        }
    }

    private fun blockClicks(sId: String) {
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


        val blockUserReq= BlockUserReq(sId)

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

    private fun homeRenderList(recommendBusinessResp: RecommmendBusinnessResp) {
        if (recommendBusinessResp!!.data.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.noResultTv.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.noResultTv.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)


    }

    private fun setupUI() {
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireActivity())


        binding.searchBarText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }
        })

    }

    private fun setupClicks() {

    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.visibility = View.GONE
        binding.noResultTv.visibility = View.VISIBLE
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.RecommendBusiness

            )
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ReferBusinessListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}