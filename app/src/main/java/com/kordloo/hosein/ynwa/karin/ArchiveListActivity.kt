package com.kordloo.hosein.ynwa.karin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kordloo.hosein.ynwa.karin.adapter.ArchiveAdapter
import com.kordloo.hosein.ynwa.karin.db.ArchiveDAO
import com.kordloo.hosein.ynwa.karin.model.Archive
import com.kordloo.hosein.ynwa.karin.model.ArchiveParc
import com.kordloo.hosein.ynwa.karin.model.WareArchiveParc
import com.kordloo.hosein.ynwa.karin.util.Keys
import kotlinx.android.synthetic.main.activity_archive_list.*

class ArchiveListActivity : AppCompatActivity() {

    private val adapter = ArchiveAdapter()
    private val layoutManager = LinearLayoutManager(this)
    private val archiveDAO = ArchiveDAO()
    var archive: Archive? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive_list)
        init()
    }

    private fun init() {
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        adapter.setOnItemListener(object: OnItemListener<Archive>{
            override fun onClicked(archive: Archive) {
                val wareList = arrayListOf<WareArchiveParc>()
                for (i in 0 until archive.waresName.size) {
                    val wap = WareArchiveParc(archive.waresName[i]!!, archive.waresPrice[i]!!, archive.waresCount[i]!!)
                    wareList.add(wap)
                }

                val archiveParc =
                    ArchiveParc(archive.customerId, archive.customerName, archive.customerPhone,
                    wareList, archive.date, archive.totalWares, archive.totalPrize, archive.check, archive.naghd)

               val i = Intent(this@ArchiveListActivity, FactorActivity::class.java)
                i.putExtra("ArchiveListActivity", 2000)
                i.putExtra(Keys.CUSTOMER_ARCHIVE, archiveParc)
                startActivity(i)
            }
        })

        val type = intent.getIntExtra(Keys.CUSTOMER_ARCHIVE, -1)
        val customerId = intent.getLongExtra(Keys.CUSTOMER, -1)

        if (type == -1) {
            val list = archiveDAO.findAll()
            if (list != null && list.size > 0)
                adapter.setList(list)
            else
                tvEmptyList.visibility = View.VISIBLE
        }
        else {
            val list = archiveDAO.findByCustomerId(customerId)
            if (list != null && list.size > 0)
                adapter.setList(list)
            else
                tvEmptyList.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        archiveDAO.close()
    }
}
