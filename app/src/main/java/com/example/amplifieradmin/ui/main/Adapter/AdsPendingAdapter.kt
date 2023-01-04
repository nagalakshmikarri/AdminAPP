package com.example.amplifieradmin.ui.main.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amplifieradmin.AdsListStatusActivity
import com.example.amplifieradmin.data.model.AcceptResp
import com.example.amplifieradmin.data.model.AdminUsersData
import com.example.amplifieradmin.data.model.AdsPendingData
import com.example.amplifieradmin.databinding.AdminSupportersBinding
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.PendingBinding
import kotlinx.android.synthetic.main.pending.view.*

class AdsPendingAdapter(
    private var adsPendingData: List<AdsPendingData>,
            private val context: Context,
    private var onAcceptClick: OnAcceptClick,
    private var onRejectClick:OnRejectClick,
) : RecyclerView.Adapter<AdsPendingAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: PendingBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            adsPendingData: AdsPendingData,
            context: Context,
        ) {
           Glide.with(context).load(adsPendingData.s_user_img).into(binding.userpic)
            binding.userNameTv.text = adsPendingData.s_business
            binding.title.text=adsPendingData.title
            binding.description.text=adsPendingData.descript
            binding.tvWatchnow.text=adsPendingData.link_name
            Glide.with(context).load(adsPendingData.img).into(binding.bannerImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AdsPendingAdapter.DataViewHolder(
        PendingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int =
        adsPendingData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: AdsPendingAdapter.DataViewHolder, position: Int) {
        holder.bind(
            adsPendingData[position],
            context,
        )
        holder.itemView.accepted_btn.setOnClickListener {
            val dialog = Dialog(context)
            val inflaterBlock =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bindingDialog = AlertDialogBinding.inflate(inflaterBlock)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(bindingDialog.root)

            bindingDialog.tvSubTitle.text = "Are you sure do you want to Accept Post?"
            bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
            bindingDialog.cancelIv.setOnClickListener { dialog.dismiss() }


            bindingDialog.btnYes.setOnClickListener {
                onAcceptClick.onAcceptClick(adsPendingData[position].id, "1")
                dialog.dismiss()
            }

            dialog.show()


        }
        holder.itemView.rejected_btn.setOnClickListener {
            val dialog = Dialog(context)
            val inflaterBlock =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bindingDialog = AlertDialogBinding.inflate(inflaterBlock)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(bindingDialog.root)

            bindingDialog.tvSubTitle.text = "Are you sure do you want to Reject Post?"
            bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
            bindingDialog.cancelIv.setOnClickListener { dialog.dismiss() }


            bindingDialog.btnYes.setOnClickListener {
                onRejectClick.onRejectClick(adsPendingData[position].id,"1")
                dialog.dismiss()
            }

            dialog.show()


        }



    }

    fun filterList(adminUsersData: MutableList<AdsPendingData>) {
        this.adsPendingData = adsPendingData
        notifyDataSetChanged()
    }

    fun addData(list: List<AdsPendingData>) {
        this.adsPendingData = list
    }
        interface OnAcceptClick{
            fun onAcceptClick(acceptdId: String,accept: String)
        }
    interface OnRejectClick{
        fun onRejectClick(RejectedId:String,reject:String)
    }

}