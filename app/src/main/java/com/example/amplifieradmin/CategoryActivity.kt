package com.example.amplifieradmin

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amplifieradmin.data.api.ApiHelperImpl
import com.example.amplifieradmin.data.api.RetrofitBuilder
import com.example.amplifieradmin.data.model.GetCategoryResp
import com.example.amplifieradmin.data.model.GetCategoryRespData
import com.example.amplifieradmin.data.model.GetTagsData
import com.example.amplifieradmin.databinding.ActivityCategoryBinding
import com.example.amplifieradmin.helper.PrefHelper
import com.example.amplifieradmin.ui.main.Adapter.CategoryAdapter
import com.example.amplifieradmin.ui.main.Adapter.TagsSpinnerAdapter
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.util.ViewModelFactory
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

class CategoryActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    private lateinit var destinationAdapter: TagsSpinnerAdapter
    private var subAdmin_id = ""
    private var typeId = "-1"
    var getCategoryResp: GetCategoryResp? = null
    lateinit var prefHelper: PrefHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
        setupUI()
        setupViewModel()
        observeViewModel()
        callTypesAPI()
    }

    private fun callTypesAPI() {
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.GetTags
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
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Error -> {
                        Log.e("testtt", "Not found/Error")
                        binding.progressBar.visibility = View.GONE
                        //Toast.makeText(context, R.string.datanotfound, Toast.LENGTH_LONG).show()
                    }

                    is MainState.Get_Tags -> {
                        binding.progressBar.visibility = View.GONE

                        if (it.getTagsResp?.status.equals("ok")) {
                            typesRenderAdapter(it.getTagsResp!!.data)
                        } else {
                            Toast.makeText(
                                this@CategoryActivity,
                                it.getTagsResp?.status,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    is MainState.Category -> {
                        Log.e("testtt", it.categoryResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        lifecycleScope.launch {
                            homeViewModel.homeIntent.send(
                                MainIntent.GetCategory
                            )

                        }


                    }
                    is MainState.Get_Category -> {
                        Log.e("testsssstt", it.getCategoryResp?.status.toString())
                        binding.progressBar.visibility = View.GONE
                        getCategoryResp = it.getCategoryResp
                        binding.categoryEt.text.clear()
                       // binding.spDestinations.text.clear()
                        if (it.getCategoryResp.list.isNotEmpty()) {
                            binding.servicessRecyc.visibility = View.VISIBLE
                            binding.noTextTv.visibility = View.GONE
                        } else {
                            binding.servicessRecyc.visibility = View.GONE
                            binding.noTextTv.visibility = View.VISIBLE
                        }

                        adapter = CategoryAdapter(it.getCategoryResp.list,this@CategoryActivity,
                           )
                        binding.servicessRecyc.adapter = adapter
                        homeRenderList(it.getCategoryResp!!.list)

                    }
                    is MainState.Error -> {
                        Log.e("testtt", "Error")
                        binding.progressBar.visibility = View.GONE
                        /* Toast.makeText(this@CatlogProductesActivity, it.error, Toast.LENGTH_LONG)
                             .show()*/
                    }
                    else -> {}
                }
            }
        }

    }

    private fun typesRenderAdapter(data: List<GetTagsData>) {
        val filteredTypes = ArrayList<GetTagsData>()
        filteredTypes.add(GetTagsData("-1","select a type",""))
        filteredTypes.addAll(data)

        Log.e("jgjkbm", filteredTypes.toString())


        if (filteredTypes.isNotEmpty()) {
            destinationAdapter =
                TagsSpinnerAdapter(
                    this@CategoryActivity,
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
                            destinationAdapter.list[destinationAdapter.selectedId].id.toString()
                        )

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            binding.spDestinations.adapter = destinationAdapter

        }

    }

    private fun homeRenderList(list: List<GetCategoryRespData>) {
        if (list!!.isNotEmpty()) {
            binding.servicessRecyc.visibility = View.VISIBLE
            binding.noTextTv.visibility = View.GONE
        } else {
            binding.servicessRecyc.visibility = View.GONE
            binding.noTextTv.visibility = View.VISIBLE
        }
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
                .get(HomeViewModel::class.java)
        lifecycleScope.launch {
            homeViewModel.homeIntent.send(
                MainIntent.GetCategory
            )
        }
    }
    private fun setupUI() {
        prefHelper=PrefHelper(this)
        binding.servicessRecyc.addItemDecoration(
            DividerItemDecoration(
                this@CategoryActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.servicessRecyc.layoutManager = LinearLayoutManager(this@CategoryActivity)
    }
    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.saveButton.setOnClickListener {
            if (this::destinationAdapter.isInitialized) {
                if (!destinationAdapter.list[destinationAdapter.selectedId].id.equals(
                        "-1",
                        true
                    )
                ) {
                    typeId =
                        destinationAdapter.list.get(destinationAdapter.selectedId).id.toString()
                }
            }
            if (checkValidations()) {
                lifecycleScope.launch {
                    homeViewModel.homeIntent.send(
                        MainIntent.Category(
                            // prefHelper.getString(Constants.PREF_ADMINID)!!,
                            binding.categoryEt.text.toString(),
                         typeId
                        )
                    )

                }
            }

        }
    /*    binding.categoryEt.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.categoryEt.text.toString() != "") {
                    lifecycleScope.launch {
                        homeViewModel.homeIntent.send(
                            MainIntent.Category(
                               // prefHelper.getString(Constants.PREF_ADMINID)!!,
                                binding.categoryEt.text.toString()
                            )
                        )

                    }
                } else {

                }
            }
            false
        })*/
    }

    private fun checkValidations(): Boolean {
        if (binding.categoryEt.text.toString().trim() == "") {
            binding.categoryEt.error = "Please enter Category"
            return false
        }
        if (typeId.equals("-1")) {
            showMessage("Please select Tag")
            return false
        }

        return true
    }
    private fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }
}