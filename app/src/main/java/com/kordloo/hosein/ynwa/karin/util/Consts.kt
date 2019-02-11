package com.kordloo.hosein.ynwa.karin.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.kordloo.hosein.ynwa.karin.MyApplication
import com.kordloo.hosein.ynwa.karin.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import com.itextpdf.text.DocumentException
import com.itextpdf.text.BadElementException
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import java.io.IOException
import java.net.MalformedURLException
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.opengl.ETC1.getHeight
import android.view.View


class Utils {

    companion object {

        private val context = MyApplication.instance
        private val df = DecimalFormat("#,###")

        fun formatCurrency(money: Int): String {
            return df.format(money)
        }

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
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(imageView)
        }

        fun loadImage(uri: Uri, imageView: ImageView, size: Int) {
            Picasso.get()
                .load(uri)
                .resize(size, size)
                .centerCrop()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(imageView)
        }

        fun loadImage(imageView: ImageView) {
            Picasso.get()
                .load("nothing")
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
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

        fun getBitmapFromView(view: View): Bitmap {
            //Define a bitmap with the same size as the view
            val returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
            //Bind a canvas to it
            val canvas = Canvas(returnedBitmap)
            //Get the view's background
            val bgDrawable = view.getBackground()
            if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
                bgDrawable!!.draw(canvas)
            else
            //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE)
            // draw the view on the canvas
            view.draw(canvas)
            //return the bitmap
            return returnedBitmap
        }

        fun addImage(document: Document, byteArray: ByteArray) {
            var image: Image? = null
            try {
                image = Image.getInstance(byteArray)
            } catch (e: BadElementException) {
                e.printStackTrace()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // image.scaleAbsolute(150f, 150f);
            try {
                document.add(image)
            } catch (e: DocumentException) {
                e.printStackTrace()
            }

        }

    }
}

object Keys {
    const val CUSTOMER = "Key_Customer"
    const val REGISTER_ORDER = "REGISTER_ORDER"
}
