package com.kordloo.hosein.ynwa.karin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.kordloo.hosein.ynwa.karin.adapter.ShopAdapter
import com.kordloo.hosein.ynwa.karin.db.WareDAO
import com.kordloo.hosein.ynwa.karin.model.Customer
import com.kordloo.hosein.ynwa.karin.model.FinalOrder
import com.kordloo.hosein.ynwa.karin.model.Order
import com.kordloo.hosein.ynwa.karin.model.Ware
import com.kordloo.hosein.ynwa.karin.util.Keys
import com.kordloo.hosein.ynwa.karin.util.Toaster
import com.kordloo.hosein.ynwa.karin.util.Utils
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity(), OnShopItemListener<Ware, String> {

    private val wareDAO = WareDAO()
    private var customer = Customer()
    private var adapter = ShopAdapter()
    private val layoutManager = GridLayoutManager(this, 2)
    private var orderList = arrayListOf<Order>()
    private var isDuplicate = false
    private var sumWareNumbers = 0
    private var sumWarePrize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        init()
    }

    private fun init() {
        customer = intent.getParcelableExtra(Keys.CUSTOMER)
        tvName.text = customer.name
        tvPhone.text = "تلفن :  ${customer.phone}"

        val date = JalaliDate.getCurrentShamsidate()
        val txtDate = "تاریخ: \n $date"
        tvDate.text = txtDate

        adapter.onShopListener(this)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        val list = wareDAO.findAll()
        if (list != null && list.size > 0)
            adapter.setList(list)
        else
            tvEmptyList.visibility = View.VISIBLE

        btnRegister.setOnClickListener {
            val finalOrderList = arrayListOf<Order>()
            for (o in orderList) {
                if (o.count != 0)
                    finalOrderList.add(o)
            }
           if (sumWareNumbers > 0) {
               val finalOrder = FinalOrder(customer, finalOrderList, date, sumWarePrize, sumWareNumbers)
               val intent = Intent(this, FactorActivity::class.java)
               intent.putExtra(Keys.REGISTER_ORDER, finalOrder)
               startActivity(intent)
           }
            else
               Toaster.show("موردی انتخاب نکرده اید")
        }
    }

    override fun onClick(ware: Ware, count: String) {
        setOrderList(ware, count)
        setFactor()
    }

    private fun setFactor() {
        sumWareNumbers = 0
        sumWarePrize = 0
        for (o in orderList) {
            sumWareNumbers += o.count
            sumWarePrize += o.Ware.price.toInt() * o.count
        }

        tvTotal.text = Utils.formatCurrency(sumWareNumbers)
        tvPayMoney.text = Utils.formatCurrency(sumWarePrize)
    }

    private fun setOrderList(ware: Ware, count: String) {
        val countInt = count.toInt()
        val newOrder = Order(ware, countInt)
        if (!orderList.isNullOrEmpty()) {
            for (i in 0 until orderList.size) {
                val existOrder = orderList[i]
                if (existOrder.Ware == newOrder.Ware ) {
                    isDuplicate = true
                    orderList[i] = newOrder
                    return
                }
                else
                    isDuplicate = false
            }

            if (!isDuplicate)
                orderList.add(newOrder)
        }
        else
            orderList.add(newOrder)


    }

    override fun onDestroy() {
        super.onDestroy()
        wareDAO.close()
    }
}
