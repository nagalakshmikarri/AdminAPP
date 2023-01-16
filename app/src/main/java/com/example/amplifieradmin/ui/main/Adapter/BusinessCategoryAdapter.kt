package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.GetCategoryRespData
import com.example.amplifieradmin.databinding.BusinessscategoryBinding
import com.example.amplifieradmin.databinding.CategoryBinding
import kotlinx.android.synthetic.main.businessscategory.view.*

class BusinessCategoryAdapter(
    private var getCategoryData: List<GetCategoryRespData>,
    private val context: Context,
    private val onItemClick: OnItemClick
): RecyclerView.Adapter<BusinessCategoryAdapter.DataViewHolder>() {
    public var selectedPosition = -1
    public var selectedCat = ""
    class DataViewHolder(itemView: BusinessscategoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            getCategoryData: GetCategoryRespData,
            context: Context,
        ) {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BusinessCategoryAdapter.DataViewHolder(
            BusinessscategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int =
        getCategoryData.size
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: BusinessCategoryAdapter.DataViewHolder, position: Int) {
        holder.bind(
            getCategoryData[position],
            context,
        )
        val serviceList = getCategoryData[position]
        holder.itemView.artcenter.text = serviceList.name
        holder.itemView.check_box.isChecked = selectedPosition == position

        if (selectedPosition == -1) {
            holder.itemView.check_box.isChecked = false
        } else {
            holder.itemView.check_box.isChecked = selectedPosition == position
        }
        holder.itemView.business_rl.setOnClickListener(View.OnClickListener {
            holder.itemView.check_box.isChecked = true
            selectedCat = ""
            if (selectedPosition != position) {
                notifyItemChanged(selectedPosition)
                selectedPosition = position
                onItemClick.onClick(serviceList)
            }
        })


        if (serviceList.id == selectedCat) {
            holder.itemView.check_box.isChecked = true
            selectedPosition=position
        }

    }
    fun filterList(getCategoryData: MutableList<GetCategoryRespData>) {
        this.getCategoryData = getCategoryData
        notifyDataSetChanged()
    }
    interface OnItemClick {
        fun onClick(getCatData: GetCategoryRespData)
    }
    fun addData(list: List<GetCategoryRespData>) {
        this.getCategoryData = list
    }



}