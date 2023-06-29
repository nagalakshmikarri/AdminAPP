package com.example.amplifieradmin.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.AllCliaedBusinessResp
import com.example.amplifieradmin.data.model.GetCitiesReq
import com.example.amplifieradmin.data.model.GetCitiesRespData
import com.example.amplifieradmin.data.model.GetTagsData
import com.example.amplifieradmin.databinding.FragmentAllBusinessBinding
import com.example.amplifieradmin.databinding.FragmentPriorityListBinding
import com.example.amplifieradmin.ui.main.Adapter.*
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch
import java.util.ArrayList


class AllBusinessFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: AllCliamBusinessListAdapter
    private var _binding: FragmentAllBusinessBinding? = null
    private lateinit var citiesAdapter: CitiesSpinnerAdapter
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBusinessBinding.inflate(inflater, container, false)
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
                    is MainState.AllCliamedBusiness -> {
                        Log.e("testtt", it.allCliaedBusinessResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter = AllCliamBusinessListAdapter(
                            it.allCliaedBusinessResp!!.data,
                            requireActivity()
                        )
                        binding.allBusinessListRecy.adapter = adapter
                        homeRenderList(it.allCliaedBusinessResp)

                    }

                    is MainState.GetCities -> {
                        binding.progressBar.visibility = View.GONE

                        if (it.getCitiesResp?.status.equals("ok")) {
                            typesRenderAdapter(it.getCitiesResp!!.data)
                        }
                    }


                    else -> {}
                }
            }
        }

    }

    private fun typesRenderAdapter(data: List<GetCitiesRespData>) {
        val filteredTypes = ArrayList<GetCitiesRespData>()
        filteredTypes.add(GetCitiesRespData(""))
        filteredTypes.addAll(data)

        Log.e("jgjkbm", filteredTypes.toString())


        if (filteredTypes.isNotEmpty()) {
            citiesAdapter =
                CitiesSpinnerAdapter(
                    requireActivity(),
                    true,
                    filteredTypes,
                    View.OnClickListener {
                        binding.spDestinations.performClick()
                    })
            binding.spDestinations.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        citiesAdapter.updateSelection(position)
                        Log.e(
                            "jhfdgjghk",
                            citiesAdapter.list[citiesAdapter.selectedId].toString()
                        )

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            binding.spDestinations.adapter = citiesAdapter

        }

    }

    private fun homeRenderList(allCliaedBusinessResp: AllCliaedBusinessResp) {
        if (allCliaedBusinessResp!!.data.isNotEmpty()) {
            binding.allBusinessListRecy.visibility = View.VISIBLE
            //  binding.noResultTv.visibility = View.GONE
        } else {
            binding.allBusinessListRecy.visibility = View.GONE
            //    binding.noResultTv.visibility = View.VISIBLE
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

    }

    private fun setupUI() {
        binding.allBusinessListRecy.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.allBusinessListRecy.layoutManager =
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
                MainIntent.AllClaimedBussiness

            )
        }

        val getCitiesReq = GetCitiesReq("")
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.GetCities(
                    getCitiesReq
                )

            )
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            AllBusinessFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}