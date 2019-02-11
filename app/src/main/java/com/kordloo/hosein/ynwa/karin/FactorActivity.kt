package com.kordloo.hosein.ynwa.karin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.kordloo.hosein.ynwa.karin.adapter.FactorAdapter
import com.kordloo.hosein.ynwa.karin.model.FinalOrder
import com.kordloo.hosein.ynwa.karin.util.Keys
import com.kordloo.hosein.ynwa.karin.util.Toaster
import kotlinx.android.synthetic.main.activity_factor.*

class FactorActivity : AppCompatActivity() {

    private var finalOrder = FinalOrder()
    private val adapter = FactorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factor)
        init()
        Toaster.show(finalOrder.toString(), Toast.LENGTH_LONG)
    }

    private fun init() {
        finalOrder = intent.getParcelableExtra(Keys.REGISTER_ORDER)

        val name = finalOrder.customer.name
        val phone = finalOrder.customer.phone
        tvCustomer.text = "$name  $phone"

        tvDate.text = "تاریخ : ${finalOrder.date}"

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        adapter.setList(finalOrder.orderList)

    }
}
