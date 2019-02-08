package com.kordloo.hosein.ynwa.karin

import android.content.Context
import android.widget.Toast

class Toaster {

    companion object {
        fun show(context: Context, msg: String, duration: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(context, msg, duration).show()
        }
    }

}