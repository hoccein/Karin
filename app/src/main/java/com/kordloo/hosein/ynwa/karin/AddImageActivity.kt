package com.kordloo.hosein.ynwa.karin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.LinearLayout
import com.kordloo.hosein.ynwa.karin.db.WareDAO
import com.kordloo.hosein.ynwa.karin.event.WareEvent
import com.kordloo.hosein.ynwa.karin.model.Ware
import com.kordloo.hosein.ynwa.karin.util.Toaster
import com.kordloo.hosein.ynwa.karin.util.Utils
import kotlinx.android.synthetic.main.activity_add_image.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File


class AddImageActivity : AppCompatActivity() {

    private var path = ""
    private val wareDAO = WareDAO()
    private var ware = Ware()
    private var isEdit = false

    companion object {
        const val REQ_Gallery = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)
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

        add.setOnClickListener { onAddClick() }
    }

    private fun openGallery() {
        val i = Intent()
        i.action = Intent.ACTION_GET_CONTENT
        i.type = "image/*"
        startActivityForResult(i, REQ_Gallery)
    }

    private fun onAddClick() {
        if (TextUtils.isEmpty(path)) {
            Toaster.show("یک عکس انتخاب کنید")
            return
        }

        if (TextUtils.isEmpty(etWare.text.toString())) {
            Toaster.show("نام محصول را وارد کنید")
            return
        }

        if (TextUtils.isEmpty(etMoney.text.toString())) {
            Toaster.show("قیمت محصول را وارد کنید")
            return
        }

        val w =
            if (isEdit)
                Ware(ware.id, etWare.text.toString(), etMoney.text.toString(), path)
            else
                Ware(name = etWare.text.toString(), price = etMoney.text.toString(), path = path)

        wareDAO.save(w)
        resetViews()
        Toaster.show(getString(R.string.successRegister))

        if (isEdit)
            finish()
    }

    private fun resetViews() {
        path = ""
        image.setImageResource(R.drawable.ic_image)
        etWare.text = null
        etMoney.text = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_Gallery && resultCode == Activity.RESULT_OK) {
            val imgUri = data?.data
            path = Utils.getPath(this, imgUri!!)
            setImage(path)
        }
    }

    private fun setImage(path: String) {
        val u = Uri.fromFile(File(path))
        Utils.loadImage(u, image)
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(wareEvent: WareEvent) {
        EventBus.getDefault().removeStickyEvent(wareEvent)
        this.ware = wareEvent.ware
        isEdit = true
        path = ware.path
        setImage(ware.path)
        etWare.setText(ware.name)
        etMoney.setText(ware.price)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        wareDAO.close()
    }

}