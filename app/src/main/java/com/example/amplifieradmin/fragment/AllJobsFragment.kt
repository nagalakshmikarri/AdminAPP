package com.example.amplifieradmin.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.BusinessInfoActivity
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.AllJobsResp
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.databinding.FragmentAllJobsBinding
import com.example.amplifieradmin.databinding.FragmentReferBusinessListBinding
import com.example.amplifieradmin.ui.main.Adapter.AllJobAdapter
import com.example.amplifieradmin.ui.main.Adapter.RecommendBusinessAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch


class AllJobsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: AllJobAdapter
    private var _binding: FragmentAllJobsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllJobsBinding.inflate(inflater, container, false)
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
                    is MainState.AllJobs -> {
                        Log.e("testtt", it.allJobsResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = AllJobAdapter(
                            it.allJobsResp!!.data,
                            requireActivity(),
                            )
                        binding.allJobsRecy.adapter = adapter
                        homeRenderList(it.allJobsResp)

                    }


                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(allJobsResp: AllJobsResp) {
        if (allJobsResp!!.data.isNotEmpty()) {
            binding.allJobsRecy.visibility = View.VISIBLE
            binding.noResultTv.visibility = View.GONE
        } else {
            binding.allJobsRecy.visibility = View.GONE
            binding.noResultTv.visibility = View.VISIBLE
        }
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)


    }

    private fun setupUI() {
        binding.allJobsRecy.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.allJobsRecy.layoutManager =
            LinearLayoutManager(requireActivity())
    }
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.AllJobs

            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AllJobsFragment().apply {
            }
    }
}