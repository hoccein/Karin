package com.kordloo.hosein.ynwa.karin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.kordloo.hosein.ynwa.karin.adapter.GalleryAdapter
import com.kordloo.hosein.ynwa.karin.db.WareDAO
import com.kordloo.hosein.ynwa.karin.event.WareEvent
import com.kordloo.hosein.ynwa.karin.model.Ware
import kotlinx.android.synthetic.main.activity_gallery.*
import org.greenrobot.eventbus.EventBus

class GalleryActivity : AppCompatActivity() {

    private val wareDAO = WareDAO()
    private var adapter = GalleryAdapter()
    private val layoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        init()
    }

    private fun init() {
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        val list = wareDAO.findAll()
        if (list != null && list.size > 0)
            adapter.setList(list)
        else
            tvEmptyList.visibility = View.VISIBLE

        adapter.onItemListener(object : OnItemListener<Ware> {
            override fun onClicked(ware: Ware) {
                EventBus.getDefault().postSticky(WareEvent(ware))
                startActivity(Intent(this@GalleryActivity, AddImageActivity::class.java))
                finish()
            }
        })

        adapter.onRemoveListener(object : OnShopItemListener<Ware, Int> {
            override fun onClick(ware: Ware, position: Int) {
                wareDAO.delete(ware.id)
                adapter.remove(ware, position)
            }

        })
    }
}
