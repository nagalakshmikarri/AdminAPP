package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.AdvertaisementsActivity
import com.example.amplifieradmin.BusinessCategoryActivity
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.model.CliamBusinessData
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.databinding.CliambusinessBinding
import com.example.amplifieradmin.databinding.RecommendbusinessBinding
import com.google.android.libraries.places.internal.it

class RecommendBusinessAdapter(
    private var recommendBusinessData: List<RecommendBusinessData>,
    private val context: Context,
) : RecyclerView.Adapter<RecommendBusinessAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: RecommendbusinessBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            recommendBusinessData: RecommendBusinessData,
            context: Context,
        ) {
            binding.tvBusiness.text = recommendBusinessData.s_business
            if (recommendBusinessData.businesscategory.isNullOrEmpty()){
                binding.tvCategory.text="Please add Business Category"
                binding.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.red));
            }else{
                binding.tvCategory.text=recommendBusinessData.businesscategory
                binding.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.purple_500));
            }
            binding.tvAddress.text = if (!recommendBusinessData.s_address.isNullOrEmpty())
                recommendBusinessData.s_address + ", " else " " +
                    if (!recommendBusinessData.s_address1.isNullOrEmpty())
                        recommendBusinessData.s_address1 + ", " else " " +
                            if (!recommendBusinessData.s_address2.isNullOrEmpty())
                                recommendBusinessData.s_address2 + ", " else " " +
                                    if (!recommendBusinessData.s_address3.isNullOrEmpty())
                                        recommendBusinessData.s_address3 + ", " else "" +
                                            if (!recommendBusinessData.city.isNullOrEmpty())
                                                recommendBusinessData.city + ", " else ""

            binding.businessCategoryTv.setOnClickListener {
                val intent = Intent(context, BusinessCategoryActivity::class.java)
                intent.putExtra("subadmin_id", recommendBusinessData.admin_id)
                intent.putExtra("s_id",recommendBusinessData.s_id)
                context.startActivity(intent);

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecommendBusinessAdapter.DataViewHolder(
            RecommendbusinessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    override fun getItemCount(): Int =
        recommendBusinessData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecommendBusinessAdapter.DataViewHolder, position: Int) {
        holder.bind(
            recommendBusinessData[position],
            context,
        )

    }

    fun filterList(adminUsersData: MutableList<RecommendBusinessData>) {
        this.recommendBusinessData = recommendBusinessData
        notifyDataSetChanged()
    }

    fun addData(list: List<RecommendBusinessData>) {
        this.recommendBusinessData = list
    }

}