package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.model.CliamDetailRespData
import com.example.amplifieradmin.data.model.TypeListData
import com.example.amplifieradmin.databinding.ActivityCliamBusinessListBinding
import com.example.amplifieradmin.databinding.ActivityUserDetailsBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.TypesSpinnerAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch
import java.util.ArrayList

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityUserDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var prefHelper: PrefHelper
    private lateinit var destinationAdapter: TypesSpinnerAdapter

    private var cliamDetailRespData: CliamDetailRespData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
        setupUI()
        callTypesAPI()
        setupViewModel()
        observeViewModel()
    }

    private fun callTypesAPI() {
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.TypeList()
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


                    else -> {}
                }
            }
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
    }
}