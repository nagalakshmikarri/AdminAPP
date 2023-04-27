package com.example.amplifieradmin

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.TypeListData
import com.example.amplifieradmin.databinding.ActivityRegistrationBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.TypesSpinnerAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegistrationActivity : AppCompatActivity() {
   private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityRegistrationBinding? = null
    private  val binding get() = _binding!!
    private var selected_code: String = ""
    private lateinit var destinationAdapter: TypesSpinnerAdapter
    lateinit var prefHelper: PrefHelper
    private var locType = 1
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var lat = "0.0"
    private var sAddress: String? = "test address"
    private var zone: String? = "test zone"
    private var timezone: String? = "test timezone"
    private var admin_id: String? = ""
    private var lng = "0.0"
    private var REQUEST_PERMISSIONS_REQUEST_CODE = 100
    private var selectedDestination = ""
    private var typeId: String? = "-1"
    private var accpt: String? = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        callTypesAPI()
        observeViewModel()
        setupClicks()
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)


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
                                this@RegistrationActivity,
                                it.typesLIstResp?.status,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is MainState.Register -> {
                        binding.progressBar.visibility = View.GONE

                        if (it.registerResp?.status.equals("ok")) {
                           finish()
                            Toast.makeText(
                                this@RegistrationActivity,
                                it.registerResp?.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@RegistrationActivity,
                                it.registerResp?.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    else -> {}
                }
            }
        }
    }

    private fun typesAdapter(data: List<TypeListData>) {
        val filteredTypes = ArrayList<TypeListData>()
        filteredTypes.add(TypeListData("-1", "Select Type"))
        filteredTypes.addAll(data)

        Log.e("jgjkbm", filteredTypes.toString())


        if (filteredTypes.isNotEmpty()) {
            destinationAdapter =
                TypesSpinnerAdapter(
                    this@RegistrationActivity,
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
                            destinationAdapter.list[destinationAdapter.selectedId].typ_id.toString()
                        )

                            if (!destinationAdapter.list[destinationAdapter.selectedId].typ_id.equals(
                                    "-1",
                                    true
                                )
                            ) {
                                selectedDestination =
                                    destinationAdapter.list.get(destinationAdapter.selectedId).typ_id.toString()

                                typeId = selectedDestination.toString()
                            }
                            Log.e("TAG", "setupClicks: " + typeId)


                        typeId?.let { it1 -> Log.e("typeId", it1) }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            binding.spDestinations.adapter = destinationAdapter
        }
    }
    private fun setupUI() {
        prefHelper = PrefHelper(this)
        admin_id = prefHelper.getString(Constants.PREF_ADMINID)!!

        //getTimeZone
        val tz: TimeZone = TimeZone.getDefault()
        timezone=tz.getDisplayName(true, TimeZone.LONG)
        zone=tz.id
        binding.etTimezone.setText(timezone)
        Log.e("TAG", "setupUI: "+zone +"   "+ timezone )
    }

    private fun setupClicks() {

        binding.aceptcheck.setOnClickListener {
            accpt = "1"
            binding.aceptcheck.visibility = View.GONE
            binding.checked.visibility = View.VISIBLE
        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
            }
        binding.checked.setOnClickListener {
            accpt = "0"
            binding.checked.visibility = View.GONE
            binding.aceptcheck.visibility = View.VISIBLE
        }
        binding.visibleEye.setOnClickListener {
            binding.inVisibleEye.visibility = View.VISIBLE
            binding.visibleEye.visibility = View.GONE
            binding.passwordEt.transformationMethod = HideReturnsTransformationMethod()
        }
        binding.inVisibleEye.setOnClickListener {
            binding.visibleEye.visibility = View.VISIBLE
            binding.inVisibleEye.visibility = View.GONE
            binding.passwordEt.transformationMethod = PasswordTransformationMethod()
        }
        binding.cnfVisibleEye.setOnClickListener {
            binding.cnfInVisibleEye.visibility = View.VISIBLE
            binding.cnfVisibleEye.visibility = View.GONE
            binding.confirmpassword.transformationMethod = HideReturnsTransformationMethod()
        }
        binding.cnfInVisibleEye.setOnClickListener {
            binding.cnfVisibleEye.visibility = View.VISIBLE
            binding.cnfInVisibleEye.visibility = View.GONE
            binding.confirmpassword.transformationMethod = PasswordTransformationMethod()
        }
        binding.ccp.setOnCountryChangeListener {
            selected_code = "+" + binding.ccp.selectedCountryCode
            Log.e("mkfjg", binding.ccp.selectedCountryCode)
        }


        binding.passwordEt.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.passwordEt.transformationMethod = PasswordTransformationMethod.getInstance();
        binding.confirmpassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.confirmpassword.transformationMethod = PasswordTransformationMethod.getInstance();

        // binding.ccp.registerPhoneNumberTextView(binding.etPhoneNumber);
        selected_code = "+" + binding.ccp.defaultCountryCode
        binding.ccp.showFlag(false)
        binding.ccp.hideNameCode(true)

        binding.termsconditins.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://amplifierapp.xyz/terms")
            startActivity(openURL)
        }









        binding.registerButton.setOnClickListener {
            if (checkValidations()) {
                lifecycleScope.launch {
                    homeViewModel.homeIntent.send(
                        MainIntent.Register(

                            binding.etEmail.text.toString(),
                            selected_code,
                            binding.etPhoneNumber.text.toString(),
                            binding.etBusinessName.text.toString(),
                            binding.passwordEt.text.toString(),
                            binding.userName.text.toString(),
                            binding.etPersonname.text.toString(),
                            lat,
                            lng,
                            typeId!!,
                            sAddress!!,
                            admin_id!!,
                            timezone!!,
                            zone!!,
                        )
                    )
                }
            }
        }
    }

    private fun checkValidations(): Boolean {
        if (binding.etBusinessName.text.toString().trim().isEmpty()) {
            binding.etBusinessName.error = "Please Enter Business Name "
            return false
        }

        if (binding.etPersonname.text.toString().trim().isEmpty()) {
            binding.etPersonname.error = "Please Enter Contact Person Name "
            return false
        }

        if (binding.etPhoneNumber.text.toString().trim().isEmpty()) {
            binding.etPhoneNumber.error = "Please Enter Phone Number "
            return false
        }
        if (binding.etPhoneNumber.text.length < 10) {
            binding.etPhoneNumber.error = "Please Enter Valid Mobile Number"
            return false
        }
        if (binding.etEmail.text.toString().trim().isEmpty()) {
            binding.etEmail.error = "Please Enter Email "
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
            binding.etEmail.error = "Invalid Email format"
            return false
        }
        if (typeId.equals("-1")) {
            showMessage("Please select Type")
            return false
        }


        if (binding.etEmail.text.toString().trim().contains(" ")) {
            binding.etEmail.error = "Please Remove Extra Space in Email"
            return false
        }


        if (binding.userName.text.toString().trim().isEmpty()) {
            binding.userName.error = "Please Enter Username"
            return false
        }

        if (binding.userName.text.toString().trim().contains(" ")) {
            binding.userName.error = "Please Remove Extra Space in Username"
            return false
        }

        if (binding.passwordEt.text?.isEmpty()!!) {
            binding.passwordEt.error = "Please Enter Password"
            return false
        }
        if (binding.passwordEt.text.length <= 4) {
            showMessage("Invalid Password")
            return false
        }
        if (binding.confirmpassword.text?.isEmpty()!!) {
            binding.confirmpassword.error = "Please Enter Confirm Password"
            return false
        }
        if (!binding.passwordEt.text.toString().equals(binding.confirmpassword.text.toString())) {
            binding.confirmpassword.error = "Password and Confirm password should be same"
            return false
        }
        if (accpt.equals("0")) {
            showMessage("Plase Accept Terms and conditions")
            return false
        }

        return true

    }

    private fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

}