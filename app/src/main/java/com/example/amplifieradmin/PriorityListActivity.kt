package com.example.amplifieradmin

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.AddBusinessPriorityReq
import com.example.amplifieradmin.data.model.AddSubTypeInviteReq
import com.example.amplifieradmin.data.model.SubTypeInviteListReq
import com.example.amplifieradmin.databinding.ActivityPriorityListBinding
import com.example.amplifieradmin.ui.main.Adapter.PartiesAdapter
import com.example.amplifieradmin.ui.main.Adapter.ValidateDateSpinnerAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PriorityListActivity : AppCompatActivity() {
    private var _binding: ActivityPriorityListBinding? = null
    private val binding get() = _binding!!
    private lateinit var validateDateAdapter: ValidateDateSpinnerAdapter
    private var selectedDays: String = ""
    private var selectedStrDate = 0
    private lateinit var homeViewModel: HomeViewModel
    private var s_id = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPriorityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
        setupUI()
        setupViewModel()
        observeViewModel()
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
                    is MainState.AddBusinessPriority -> {
                        Log.e("testtt", it.addBusinessPriorityResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        finish()
                        Toast.makeText(
                            this@PriorityListActivity,
                            it.addBusinessPriorityResp?.message,
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                    else -> {}
                }
            }
        }

    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)

    }

    private fun setupUI() {
        binding.businessTv.text = intent.getStringExtra("s_business")
        binding.sBusinessTv.text = intent.getStringExtra("s_business")
        binding.categoryTv.text = "Category: ${intent.getStringExtra("category")}"
        binding.addressTv.text = intent.getStringExtra("address")
        s_id=intent.getStringExtra("s_id").toString()
        validateDatedAdapter()
    }

    private fun validateDatedAdapter() {
        val period: MutableList<String> = ArrayList<String>()

        period.add("Day")
        period.add("Week")
        period.add("Month")

        val filteredTypes = ArrayList<String>()
        filteredTypes.add("Select Day")
        filteredTypes.addAll(period)

        Log.e("jgjkbm", filteredTypes.toString())


        if (filteredTypes.isNotEmpty()) {
            validateDateAdapter =
                ValidateDateSpinnerAdapter(this, true, filteredTypes, View.OnClickListener {
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
                        validateDateAdapter.updateSelection(position)
                        Log.e(
                            "jhfdgjghk",
                            validateDateAdapter.list.get(validateDateAdapter.selectedId).toString()
                        )
                        selectedDays =
                            validateDateAdapter.list.get(validateDateAdapter.selectedId).toString()
                        /* binding.text3.text =
                             validateDateAdapter.list[validateDateAdapter.selectedId]*/

                        binding.startDateEt.setText("");

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            binding.spDestinations.adapter = validateDateAdapter

        }
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        val strCal = Calendar.getInstance()
        val startDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                strCal.set(Calendar.YEAR, year)
                strCal.set(Calendar.MONTH, monthOfYear)
                strCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                Log.e("fhj", "" + dayOfMonth)
                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                selectedStrDate = dayOfMonth
                binding.startDateEt.setText(sdf.format(strCal.time))
            }

        binding.startDateEt.setOnClickListener {
            val dialog = DatePickerDialog(
                this, startDateSetListener,
                strCal.get(Calendar.YEAR),
                strCal.get(Calendar.MONTH),
                strCal.get(Calendar.DAY_OF_MONTH)
            )
            strCal.add(Calendar.DAY_OF_MONTH, 0)
            dialog.datePicker.minDate = System.currentTimeMillis() - 1000
            dialog.show()

        }
        binding.priorityButton.setOnClickListener {
            if (checkValidations()) {
                val addBusinessPriorityReq = AddBusinessPriorityReq(
                    s_id!!, selectedDays, binding.startDateEt.text.toString(),
                    binding.amountEt.text.toString(),
                    binding.rankEt.text.toString()
                )
                lifecycleScope.launch {
                    homeViewModel.homeIntent.send(
                        MainIntent.AddBusinessPriority(
                            addBusinessPriorityReq
                        )
                    )

                }
            }

        }


    }

    private fun checkValidations(): Boolean {


        if (selectedDays.equals("-1")) {
            showMessage("Please select Advertaise Type")
            return false
        }

        if (binding.startDateEt.text.toString().trim().isEmpty()) {
            showMessage("Please Enter Start Date")
            binding.startDateEt.background = resources.getDrawable(R.drawable.border_red)
            binding.startDateEt.requestFocus()
            return false
        } else {
            binding.startDateEt.background = resources.getDrawable(R.drawable.border)
        }
        if (binding.amountEt.text.toString().trim().isEmpty()) {
            showMessage("Please Enter Amount")
            binding.amountEt.background = resources.getDrawable(R.drawable.border_red)
            binding.amountEt.requestFocus()
            return false
        } else {
            binding.amountEt.background = resources.getDrawable(R.drawable.border)
        }
        if (binding.rankEt.text.toString().trim().isEmpty()) {
            showMessage("Please Enter Rank")
            binding.rankEt.background = resources.getDrawable(R.drawable.border_red)
            binding.rankEt.requestFocus()
            return false
        } else {
            binding.rankEt.background = resources.getDrawable(R.drawable.border)
        }

        return true

    }
    private fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

}