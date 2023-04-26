package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.model.BlockJobsRespData
import com.example.amplifieradmin.databinding.BlockjoibslayoutBinding

class BlockJobsAdapter(
    private var blockJobsRespData: List<BlockJobsRespData>,
    private val context: Context,
) : RecyclerView.Adapter<BlockJobsAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: BlockjoibslayoutBinding):
        RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            blockJobsRespData: BlockJobsRespData,
            context: Context
        ) {

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(50))
            Glide.with(context).load(blockJobsRespData.user_img)
                //  .apply(requestOptions)
                .placeholder(R.drawable.user).into(binding.userpic)


            binding.userNameTv.text=blockJobsRespData.name
            binding.type.text="Posted by ${blockJobsRespData.type}"
            binding.tvTitle.text=blockJobsRespData.title
            binding.tvAddress.text=blockJobsRespData.address

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        BlockJobsAdapter. DataViewHolder(
            BlockjoibslayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        blockJobsRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder:BlockJobsAdapter.DataViewHolder,position: Int) {
        holder.bind(
            blockJobsRespData[position],
            context)

    }
    fun filterList(adminUsersData: MutableList<BlockJobsRespData>) {
        this.blockJobsRespData=blockJobsRespData
        notifyDataSetChanged()
    }
    fun addData(list: List<BlockJobsRespData>) {
        this.blockJobsRespData = list
    }
}