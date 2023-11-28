package com.my.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun View.visible(animate: Boolean = true) {
    if (animate) {
        animate().alpha(1f).setDuration(300).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })
    } else {
        visibility = View.VISIBLE
    }
}

fun View.invisible(animate: Boolean = true) {
    hide(View.INVISIBLE, animate)
}

fun View.gone(animate: Boolean = true) {
    hide(View.GONE, animate)
}


private fun View.hide(hidingStrategy: Int, animate: Boolean = true) {
    if (animate) {
        animate().alpha(0f).setDuration(300).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = hidingStrategy
            }
        })
    } else {
        visibility = hidingStrategy
    }
}



inline fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit
) {
    snackx(resources.getString(messageRes), length, f)
}

inline fun View.snackx(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}


fun datePicker(context: Context?, tv: View?, minDate: String?, isMinDate: Boolean, isDOB: Boolean): String? {
    val dateReturn = ""
    val myCalendar = Calendar.getInstance()
    val date =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            val myFormat = "yyyy-MM-dd HH:mm:ss"
            val myFormatTwo = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val sdfTwo = SimpleDateFormat(myFormatTwo, Locale.US)
            if (tv is TextView) {
                val textView = tv
                textView.text = sdfTwo.format(myCalendar.time)
                textView.setTextColor(Color.GRAY)
            }
        }
    var d: Date? = null
    try {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        d = if (minDate != null) {
            sdf.parse(minDate)
        } else {
            val cal = Calendar.getInstance()
            if (isDOB) {
            } else cal.add(Calendar.DAY_OF_MONTH, 1)

       cal.time
        }
    } catch (e: Exception) {
        e.fillInStackTrace()
    }
    val datePicker = DatePickerDialog(context!!, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH])
    if (isDOB) {
        datePicker.datePicker.maxDate = d!!.time
    } else {
        if (isMinDate) datePicker.datePicker.minDate = d!!.time
    }
    datePicker.show()
    return dateReturn
}
fun String.isValidEmail() = !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun isValidEmail(target: CharSequence): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

fun String?.defaultOnNullValue():String = this ?: ""
fun Int?.defaultOnNullValue():Int = this ?: 0
fun Double?.defaultOnNullValue():Double = this ?: 0.0
fun <T> ArrayList<T>?.defaultOnNullValue(): List<T> = this ?: emptyList()

fun getGreetingMessage():String{
    val c = Calendar.getInstance()
    val timeOfDay = c.get(Calendar.HOUR_OF_DAY)
    return when (timeOfDay) {
        in 0..11 -> "Good Morning"
        in 12..15 -> "Good Afternoon"
        in 16..20 -> "Good Evening"
        in 21..23 -> "Good Night"
        else -> "Hello"
    }
}