package com.example.amplifieradmin.ui.main.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.model.RejectAdsData
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.RejectedBinding
import com.example.amplifieradmin.helper.Constants
import com.example.amplifieradmin.helper.PrefHelper
import kotlinx.android.synthetic.main.pending.view.*
import java.text.SimpleDateFormat
import java.util.*

class RejectedAdsAdapter
    (
    private var rejectAdsData: List<RejectAdsData>,
    private val context: Context,
    private var onAcceptClick: OnAcceptClick,
    private var onLinkClick:OnLinkClick,

    ) : RecyclerView.Adapter<RejectedAdsAdapter.DataViewHolder>(){
    class DataViewHolder(itemView: RejectedBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        lateinit var prefHelper: PrefHelper

        fun bind(
            rejectAdsData: RejectAdsData,
            context: Context,
            onLinkClick: OnLinkClick,
        ) {
            prefHelper = PrefHelper(context)

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(50))
            Glide.with(context).load(rejectAdsData.s_user_img)
                //  .apply(requestOptions)
                .placeholder(R.drawable.user).into(binding.userpic)

            binding.userNameTv.text = rejectAdsData.s_business
            binding.title.text = rejectAdsData.title
            binding.description.text = rejectAdsData.descript
            binding.tvWatchnow.text = rejectAdsData.link_name
            Glide.with(context).load(rejectAdsData.img).into(binding.bannerImage)

            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "MMM dd, yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.US)
            val outputFormat = SimpleDateFormat(outputPattern, Locale.US)

            val sDate = inputFormat.parse(rejectAdsData.sdate)
            val eDate = inputFormat.parse(rejectAdsData.edate)

            binding.startDateTv.text = outputFormat.format(sDate)+" \u00b7 " +outputFormat.format(eDate)

            binding.tvWatchnow.setOnClickListener {
                if (prefHelper.getBoolean(Constants.PREF_IS_LOGIN)) {
                    onLinkClick.onLinkClick(rejectAdsData)
                    if (rejectAdsData.link_type.equals("link")) {
                        var webpage = ""
                        if (!rejectAdsData.link.startsWith("http://") && !rejectAdsData.link.startsWith("https://")) {
                            webpage = "http://" + rejectAdsData.link
                        } else {
                            webpage = rejectAdsData.link
                        }
                        val viewIntent = Intent(
                            "android.intent.action.VIEW",
                            Uri.parse(webpage)
                        )
                        context.startActivity(viewIntent)
                    } else if (rejectAdsData.link_type.equals("email")) {

                        /*ACTION_SEND action to launch an email client installed on your Android device.*/
                        val email1 = Intent(Intent.ACTION_SEND)
                        email1.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(rejectAdsData.link))
                        email1.putExtra(Intent.EXTRA_SUBJECT, "subject")
                        email1.putExtra(Intent.EXTRA_TEXT, " ")
                        email1.type = "message/rfc822"
                        context.startActivity(
                            Intent.createChooser(
                                email1,
                                "Choose an Email client :"
                            )
                        )


                    } else if (rejectAdsData.link_type.equals("phone")) {
                        val phone: String = rejectAdsData.link
                        Log.e("phonenumber", phone)
                        val phoneIntent = Intent(
                            Intent.ACTION_DIAL, Uri.fromParts(
                                "tel", phone, null
                            )
                        )
                        context.startActivity(phoneIntent)

                    } else if (rejectAdsData.link_type.equals("telegram")) {
                        try {
                            val telegram =
                                Intent(Intent.ACTION_VIEW, Uri.parse(rejectAdsData.link))
                            telegram.setPackage("org.telegram.messenger")
                            context.startActivity(telegram)
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Telegram app is not installed",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(context, R.string.pleselogin, Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RejectedAdsAdapter.DataViewHolder(
            RejectedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int =
        rejectAdsData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    fun filterList(adminUsersData: MutableList<RejectAdsData>) {
        this.rejectAdsData= rejectAdsData
        notifyDataSetChanged()
    }
    fun addData(list: List<RejectAdsData>) {
        this.rejectAdsData = list
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(
            rejectAdsData[position],
            context,
            onLinkClick
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
                onAcceptClick.onAcceptClick(rejectAdsData[position].id, "1")
                dialog.dismiss()
            }

            dialog.show()


        }


    }
    interface OnAcceptClick{
        fun onAcceptClick(acceptdId: String,accept: String)
    }
    interface OnLinkClick {
        fun onLinkClick(rejectAdsData: RejectAdsData?)
    }

}