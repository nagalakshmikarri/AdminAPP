package com.example.amplifieradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.*
import com.example.amplifieradmin.databinding.ActivityCliamBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityUserDetailsBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.CountrySpinnerAdapter
import com.example.amplifieradmin.ui.main.Adapter.StatesSpinnerAdapter
import com.example.amplifieradmin.ui.main.Adapter.TypesSpinnerAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.util.ArrayList

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityUserDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var prefHelper: PrefHelper
    private lateinit var destinationAdapter: TypesSpinnerAdapter
    private lateinit var statesAdapter: StatesSpinnerAdapter
    private lateinit var countryAdapter: CountrySpinnerAdapter

    private var busiCatId = ""

    private var cliamDetailRespData: CliamDetailRespData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        callTypesAPI()
        observeViewModel()
        setupClicks()

    }

    private fun callTypesAPI() {
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.GetCountries()
            )

        }

    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            homeViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                        Log.e("testtt", "Idle")

                    }
                    is MainState.Loading -> {
                        Log.e("testtt", "Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.TypeList -> {
                        binding.progressBar.visibility = View.GONE

                        if (it.typesLIstResp?.status.equals("ok")) {
                            typesAdapter(it.typesLIstResp!!.list)
                        } else {
                            Toast.makeText(
                                this@UserDetailsActivity,
                                it.typesLIstResp?.status,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is MainState.GetCounteries -> {
                        binding.progressBar.visibility = View.GONE

                        if (it.getCountriesResp?.status.equals("ok")) {
                            countryAdapter(it.getCountriesResp!!.list)
                        } else {
                            Toast.makeText(
                                this@UserDetailsActivity,
                                it.getCountriesResp?.status,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is MainState.States -> {
                        binding.progressBar.visibility = View.GONE

                        if (it.statesResp?.status.equals("ok")) {
                            statesAdapter(it.statesResp!!.stateList)
                        } else {
                            Toast.makeText(
                                this@UserDetailsActivity,
                                it.statesResp?.status,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    else -> {}
                }
            }
        }

    }

    private fun statesAdapter(stateList: List<StatesData>) {
        TODO("Not yet implemented")
    }

    private fun countryAdapter(list: List<GetCountriesData>) {
        val filteredTypes = ArrayList<GetCountriesData>()
        filteredTypes.add(GetCountriesData("", "-1", ""))
        filteredTypes.addAll(list)

        Log.e("jgjkbm", filteredTypes.toString())


        if (filteredTypes.isNotEmpty()) {
            countryAdapter =
                CountrySpinnerAdapter(
                    this@UserDetailsActivity,
                    true,
                    filteredTypes,
                    View.OnClickListener {
                        binding.spCountry.performClick()
                    })
            binding.spCountry.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        countryAdapter.updateSelection(position)
                        if (countryAdapter.list[countryAdapter.selectedId].id != "-1") {
                            lifecycleScope.launch {
                                homeViewModel.homeIntent.send(
                                    MainIntent.States(countryAdapter.list[countryAdapter.selectedId].id)
                                )
                                if (countryAdapter.list[countryAdapter.selectedId].name == "USA") {
                                    binding.etAreaName.visibility = View.GONE
                                    binding.tvAreaName.visibility = View.GONE
                                    // binding.etPincode.hint = "Zipcode"
                                    binding.tvPincode.text = "Zipcode*"
                                } else {
                                    binding.etAreaName.visibility = View.VISIBLE
                                    binding.tvAreaName.visibility = View.VISIBLE
                                    //  binding.etPincode.hint = "Pincode"
                                    binding.tvPincode.text = "Pincode*"
                                }

                            }
                        }
                        Log.e(
                            "jhfdgjghk",
                            countryAdapter.list[countryAdapter.selectedId].name.toString()
                        )

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            binding.spCountry.adapter = countryAdapter

        }

    }

    private fun typesAdapter(list: List<TypeListData>) {
        val filteredTypes = ArrayList<TypeListData>()
        filteredTypes.add(TypeListData("-1", "Select Type"))
        filteredTypes.addAll(list)

        Log.e("jgjkbm", filteredTypes.toString())


        if (filteredTypes.isNotEmpty()) {
            destinationAdapter =
                TypesSpinnerAdapter(
                    this@UserDetailsActivity,
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
                        destinationAdapter.updateSelection(position)
                        Log.e(
                            "jhfdgjghk",
                            destinationAdapter.list[destinationAdapter.selectedId].typ_title.toString()
                        )

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            binding.spDestinations.adapter = destinationAdapter

        }
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.TypeList()
            )

        }

    }

    private fun setupUI() {
        prefHelper = PrefHelper(this)
        cliamDetailRespData = intent.getParcelableExtra("user_details")

        //UI
       /* binding.BusinessTv.text = cliamDetailRespData?.s_business
        binding.userNameTv.text = cliamDetailRespData?.s_username
        binding.tvPhone.text = cliamDetailRespData?.s_phone
        binding.tvEmail.text = cliamDetailRespData?.s_email
*/
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }


        binding.etBusinessCat.setOnClickListener {
            val intent = Intent(this, SelectBusinessCategoryActivity::class.java)
            someActivityResultLauncher.launch(intent)
        }

    }

    private var someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // There are no request codes
            val data = result.data
            if (data!!.hasExtra("BussCat")) {
                val s = data.getStringExtra("BussCat")
                val type = object : TypeToken<GetCategoryRespData?>() {}.type
                val cat: GetCategoryRespData = Gson().fromJson(s, type)
                binding.etBusinessCat.setText(cat.name)
                busiCatId = cat.id
            }

        }
    }

}