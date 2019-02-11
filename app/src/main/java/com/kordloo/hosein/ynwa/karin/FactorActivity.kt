package com.kordloo.hosein.ynwa.karin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.kordloo.hosein.ynwa.karin.adapter.FactorAdapter
import com.kordloo.hosein.ynwa.karin.model.FinalOrder
import com.kordloo.hosein.ynwa.karin.util.Keys
import com.kordloo.hosein.ynwa.karin.util.Toaster
import com.kordloo.hosein.ynwa.karin.util.Utils
import kotlinx.android.synthetic.main.activity_factor.*
import android.system.Os.mkdir
import java.io.File
import java.nio.file.Files.exists
import android.graphics.Bitmap
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.lang.Exception
import android.widget.RelativeLayout
import android.view.LayoutInflater




class FactorActivity : AppCompatActivity() {

    private var finalOrder = FinalOrder()
    private val adapter = FactorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factor)
        init()
    }

    private fun init() {
        finalOrder = intent.getParcelableExtra(Keys.REGISTER_ORDER)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.setList(finalOrder.orderList)

        val name = finalOrder.customer.name
        val phone = finalOrder.customer.phone
        tvCustomer.text = "$name  $phone"

        tvDate.text = "تاریخ : ${finalOrder.date}"

        val totalWares = Utils.formatCurrency(finalOrder.totalWares)
        tvTotalWares.text = "تعداد کل :  $totalWares  عدد"

        val totalPrize = Utils.formatCurrency(finalOrder.totalPrize)
        tvTotalPrize.text = "مبلغ کل :  $totalPrize تومان "

        tvCheckPrize.setOnClickListener {
            onCheckPrize()
        }

        tvNaghdPrize.setOnClickListener {
            onNaghdPrize()
        }

        btnRegister.setOnClickListener {
            onRegister()
        }

    }


    private fun onCheckPrize() {

    }

    private fun onNaghdPrize() {

    }


    private fun onRegister() {
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
        val root = inflater.inflate(R.layout.activity_factor, null) as RelativeLayout
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
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}
