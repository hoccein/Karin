package com.kordloo.hosein.ynwa.karin.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.kordloo.hosein.ynwa.karin.MyApplication
import com.squareup.picasso.Picasso

class Utils {

    companion object {

        private val context = MyApplication.instance

        fun dp2px(dp: Float): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }

        fun px2dp(px: Float): Int {
            return (px / context.resources.displayMetrics.density).toInt()
        }

        fun getWidth(): Int {
            return context.resources.displayMetrics.widthPixels
        }

        fun loadImage(uri: Uri, imageView: ImageView) {
            Picasso.get()
                .load(uri)
                .into(imageView)
        }

        fun getPath(context: Context, uri: Uri): String {
            var result: String? = null
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.getContentResolver().query(uri, proj, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(proj[0])
                    result = cursor.getString(column_index)
                }
                cursor.close()
            }
            if (result == null) {
                result = "Not found"
            }
            return result
        }
    }
}
