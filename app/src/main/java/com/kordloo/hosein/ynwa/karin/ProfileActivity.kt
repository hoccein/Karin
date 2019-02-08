package com.kordloo.hosein.ynwa.karin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File
import android.provider.MediaStore
import android.util.Log
import okhttp3.internal.Util
import java.net.URI
import kotlin.math.log


class ProfileActivity : AppCompatActivity() {

    companion object {
        const val REQ_Gallery = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        addddProduct.setOnClickListener {
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

            val path = Consts.getPath(this, imgUri!!)

            val u = Uri.fromFile(File(path))

            Consts.loadImage(u, image)
        }
        else
            Toaster.show(this, getString(R.string.generalError))
    }

}