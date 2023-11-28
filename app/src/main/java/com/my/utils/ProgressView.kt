package com.my.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.my.myhisab.R

class ProgressView {

    companion object {
        fun getLoader(context: Context): Dialog {
//            val dialog = Dialog(context, R.style.Base_Theme_MyHisab)
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_loader)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
    }

}