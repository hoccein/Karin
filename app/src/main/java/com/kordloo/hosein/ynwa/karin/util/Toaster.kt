package com.kordloo.hosein.ynwa.karin.util

import android.widget.Toast
import com.kordloo.hosein.ynwa.karin.MyApplication

class Toaster {

    companion object {

        private val context = MyApplication.instance

        fun show(msg: String, duration: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(context, msg, duration).show()
        }
    }

}