package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.EditSubTypeCategoryResp
import com.example.amplifieradmin.data.model.GetCategoriesRespData
import com.example.amplifieradmin.databinding.CategoriesLayoutBinding
import kotlinx.android.synthetic.main.activity_registration.view.*
import kotlinx.android.synthetic.main.businessscategory.view.*

class CategoriesAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<CategoriesAdapter.DataViewHolder>() {
    private lateinit var getCategoriesRespData: List<GetCategoriesRespData>
    public var selectedPosition = -1
    private val selectedList: ArrayList<String> = java.util.ArrayList<String>()

    class DataViewHolder(itemView: CategoriesLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
        //    editSubType: EditSubTypeCategoryResp?
            getCategoriesRespData: GetCategoriesRespData,
            context: Context,
            selectedList: ArrayList<String>,
            onItemClick: OnItemClick
        ) {
            binding.artcenter.text=getCategoriesRespData.name

            binding.businessRl.setOnClickListener(View.OnClickListener {
                binding.checkBox.isChecked = !binding.checkBox.isChecked
                val chkStatus: Boolean = binding.checkBox.isChecked()
                Log.e("TAG", "bind: $chkStatus", )

                if ( getCategoriesRespData.status){
                    binding.checkBox.isChecked=true
                }


                if (chkStatus) {
                    selectedList.add(getCategoriesRespData.id.toString())
                    onItemClick.onClick(selectedList)
                    getCategoriesRespData.status=true
                } else {
                    selectedList.remove(getCategoriesRespData.id.toString())
                    onItemClick.onClick(selectedList)
                    getCategoriesRespData.status=false
                }
                Log.e("TAG", "onBindViewHolderfghjk:$selectedList")

            })

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoriesAdapter.DataViewHolder(
            CategoriesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    override fun getItemCount(): Int =
        getCategoriesRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(
        holder: CategoriesAdapter.DataViewHolder,
        position: Int
    ) {


        holder.bind(
        //    editSubType,
            getCategoriesRespData[position],
            context,
            selectedList,
            onItemClick
        )

/*
        for(lists in editSubType?)
        val serviceList = getCategoriesRespData[position]
*/

        holder.itemView.check_box.isChecked = selectedPosition == position

        if (selectedPosition == 1) {
            holder.itemView.check_box.isChecked = false
        } else {
            holder.itemView.check_box.isChecked = selectedPosition == position
        }

/*
        if (serviceList.selected == 1) {
            holder.itemView.check_box.isChecked = true
            selectedList.add(serviceList.id)

        }
*/
/*
        holder.itemView.business_rl.setOnClickListener(View.OnClickListener {
            holder.itemView.check_box.isChecked = true
            if (selectedPosition != position) {
                notifyItemChanged(selectedPosition)
                selectedPosition = position
            }
        })
*/
      /* holder.itemView.business_rl.setOnClickListener(View.OnClickListener {
            holder.itemView.check_box.isChecked = !holder.itemView.check_box.isChecked
            val chkStatus: Boolean = holder.itemView.check_box.isChecked()
            if (chkStatus) {
*//*                notifyItemChanged(selectedPosition)
                selectedPosition = position*//*
              //  selectedList.add(serviceList.id)


            } else {
*//*                notifyItemChanged(selectedPosition)
                selectedPosition = position*//*
              //  selectedList.remove(serviceList.id)

            }
            Log.e("TAG", "onBindViewHolder: " + selectedPosition)
        })*/


    }
    fun filterList(getCategoriesRespData: MutableList<GetCategoriesRespData>) {
        this.getCategoriesRespData = getCategoriesRespData
    }

    fun addData(list: List<GetCategoriesRespData>) {
        this.getCategoriesRespData = list
    }
    interface OnItemClick {
        fun onClick(list: ArrayList<String>)
    }

}