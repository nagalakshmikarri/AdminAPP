package com.example.amplifieradmin.fragment

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
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.PriorityListRsp
import com.example.amplifieradmin.databinding.FragmentAllJobsBinding
import com.example.amplifieradmin.databinding.FragmentPriorityListBinding
import com.example.amplifieradmin.ui.main.Adapter.AllJobAdapter
import com.example.amplifieradmin.ui.main.Adapter.PriorityListAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch


class PriorityListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: PriorityListAdapter
    private var _binding: FragmentPriorityListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPriorityListBinding.inflate(inflater, container, false)
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
                    is MainState.PriorityList -> {
                        Log.e("testtt", it.priorityListRsp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = PriorityListAdapter(
                            it.priorityListRsp!!.data,
                            requireActivity()
                        )
                        binding.priorityListRecy.adapter = adapter
                        homeRenderList(it.priorityListRsp)

                    }

                    else -> {}
                }
            }
        }

    }

    private fun homeRenderList(priorityListRsp: PriorityListRsp) {
        if (priorityListRsp!!.data.isNotEmpty()) {
            binding.priorityListRecy.visibility = View.VISIBLE
          //  binding.noResultTv.visibility = View.GONE
        } else {
            binding.priorityListRecy.visibility = View.GONE
        //    binding.noResultTv.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

    }

    private fun setupUI() {
        binding.priorityListRecy.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.priorityListRecy.layoutManager =
            LinearLayoutManager(requireActivity())

    }
    override fun onResume() {
        super.onResume()

/*
        binding.allJobsRecy.visibility = View.GONE
        binding.noResultTv.visibility = View.VISIBLE

*/
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.PriorityList

            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PriorityListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}