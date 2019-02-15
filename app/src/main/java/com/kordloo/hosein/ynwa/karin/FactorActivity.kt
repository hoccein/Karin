package com.kordloo.hosein.ynwa.karin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ScrollView
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import com.kordloo.hosein.ynwa.karin.adapter.FactorAdapter
import com.kordloo.hosein.ynwa.karin.db.ArchiveDAO
import com.kordloo.hosein.ynwa.karin.model.*
import com.kordloo.hosein.ynwa.karin.util.Keys
import com.kordloo.hosein.ynwa.karin.util.Toaster
import com.kordloo.hosein.ynwa.karin.util.Utils
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_factor.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class FactorActivity : AppCompatActivity() {

    private var archiveDAO = ArchiveDAO()
    private var finalOrder = FinalOrder()
    private val adapter = FactorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factor)
        init()
    }

    private fun init() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        progressbar.visibility = View.GONE

        val type = intent.getIntExtra("ArchiveListActivity", -1)
        val archive = intent.getParcelableExtra<ArchiveParc>(Keys.CUSTOMER_ARCHIVE)
        if (type == 2000) {
            val orderList = ArrayList<Order>()
            for (i in archive.wares) {
                val order = Order(i.wareName, i.warePrice, i.wareCount)
                orderList.add(order)
            }
            setList(orderList)
            customerInfo(archive.customerName, archive.customerPhone)
            date(archive.date)
            totalWares(archive.totalWares)
            totalPrize(archive.totalPrize)
        }
        else {
            finalOrder = intent.getParcelableExtra(Keys.REGISTER_ORDER)
            setList(finalOrder.orderList)
            customerInfo(finalOrder.customer.name, finalOrder.customer.phone, finalOrder.customer.address)
            date(finalOrder.date)
            totalWares(finalOrder.totalWares)
            totalPrize(finalOrder.totalPrize)
        }


        tvCheckPrize.setOnClickListener {
//            onCheckPrize()
        }

        tvNaghdPrize.setOnClickListener {
//            onNaghdPrize()
        }

        btnRegister.visibility = if (type == 2000)
            View.GONE
        else
            View.VISIBLE

        btnRegister.setOnClickListener {
            progressbar.visibility = View.VISIBLE
            btnRegister.isEnabled = false
            saveToDB()
            Handler().postDelayed({ savePdf() }, 1500)
        }

    }

    private fun setList(orderList: ArrayList<Order>) {
        adapter.setList(orderList)
    }

    private fun customerInfo(name: String, phone: String, address: String = "") {
        tvCustomerName.text = name
        if (!TextUtils.isEmpty(address))
            tvCustomerPhoneAddress.text = "$address - $phone"
        else {
            tvCustomerName.text = "$name - $phone"
            tvCustomerPhoneAddress.visibility = View.GONE
        }
    }

    private fun date(date: String) {
        tvDate.text = "تاریخ : $date"
    }

    private fun totalWares(totalWares: Int) {
        val total = Utils.formatCurrency(totalWares)
        tvTotalWares.text = "تعداد کل :  $total  عدد"
    }

    private fun totalPrize(totalPrize: Int) {
        val total = Utils.formatCurrency(totalPrize)
        tvTotalPrize.text = "مبلغ کل :  $total تومان "
    }

    private fun saveToDB() {
        val wareNames = RealmList<String>()
        val warePrices = RealmList<String>()
        val wareCounts = RealmList<Int>()
        for (fo in finalOrder.orderList) {
            wareNames.add(fo.Ware.name)
            warePrices.add(fo.Ware.price)
            wareCounts.add(fo.count)
        }
        val archive = Archive(0,
            finalOrder.customer.id,
            finalOrder.customer.name,
            finalOrder.customer.phone,
            finalOrder.customer.address,
            wareNames,
            warePrices,
            wareCounts,
            finalOrder.date,
            finalOrder.totalWares,
            finalOrder.totalPrize)
        archiveDAO.save(archive)
    }

    private fun savePdf() {
        //First Check if the external storage is writable
        val state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED != state) {
            Toaster.show("$state : false")
        }

        //Create a directory for your PDF
        val pdfDir = File(Environment.getExternalStorageDirectory(), "Karin-pdf")
        if (!pdfDir.exists()) {
            pdfDir.mkdirs()
        }

        //Then take the screen shot
//        val v1 = root.rootView
//        v1.isDrawingCacheEnabled = true
//        val screen = Bitmap.createBitmap(v1.drawingCache)
//        v1.isDrawingCacheEnabled = false

        //Now create the name of your PDF file that you will generate
        val pdfName = "${finalOrder.customer.name}-${System.currentTimeMillis()}"
        val pdfFile = File(pdfDir, "$pdfName.pdf")


        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //RelativeLayout is root view of my UI(xml) file.
        val root = inflater.inflate(R.layout.activity_factor, null) as ScrollView
        root.isDrawingCacheEnabled = true
        // here give id of our root layout (here its my RelativeLayout's id)
        val screen = Utils.getBitmapFromView(this.window.findViewById(R.id.root))


        try {
            val document = Document()
            PdfWriter.getInstance(document, FileOutputStream(pdfFile))
            document.open()
            val stream = ByteArrayOutputStream()
            screen.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray= stream.toByteArray()
            Utils.addImage(document,byteArray)
            document.close()
            progressbar.visibility = View.GONE
            Toaster.show("ثبت شد")
            Handler().postDelayed({goToHome()}, 1000)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        archiveDAO.close()
    }
}
