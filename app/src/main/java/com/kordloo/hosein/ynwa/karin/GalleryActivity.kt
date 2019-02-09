package com.kordloo.hosein.ynwa.karin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.kordloo.hosein.ynwa.karin.util.Toaster
import com.kordloo.hosein.ynwa.karin.util.Utils
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.File


class GalleryActivity : AppCompatActivity() {

    companion object {
        const val REQ_Gallery = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        init()
    }

    private fun init() {
        // Square size for image
        var width = Utils.getWidth()
//        width = (width * 4) / 5
        val imgParams = LinearLayout.LayoutParams(width, width)
        image.layoutParams = imgParams

        // Listeners
        image.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val i = Intent()
        i.action = Intent.ACTION_GET_CONTENT
        i.type = "image/*"
        startActivityForResult(i, REQ_Gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_Gallery && resultCode == Activity.RESULT_OK) {
            val imgUri = data?.data
            val path = Utils.getPath(this, imgUri!!)
            val u = Uri.fromFile(File(path))

            Utils.loadImage(u, image)
        }
        else
            Toaster.show(getString(R.string.generalError))
    }

}