package com.kordloo.hosein.ynwa.karin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kordloo.hosein.ynwa.karin.R
import com.kordloo.hosein.ynwa.karin.model.Order
import com.kordloo.hosein.ynwa.karin.util.Utils

class FactorAdapter : RecyclerView.Adapter<FactorAdapter.MyVH>() {

    private var list: List<Order>? = null

    fun setList(list: ArrayList<Order>) {
        if (list == null)
            return
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_factor, parent, false)
        return MyVH(view)
    }

    override fun getItemCount(): Int {
        return if (list == null)
            0
        else
            list?.size!!
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.onBind(list!![position], position)
    }

    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

       private val row = itemView.findViewById(R.id.tvRow) as TextView
       private val wareName = itemView.findViewById(R.id.tvWareName) as TextView
       private val fee = itemView.findViewById(R.id.tvFee) as TextView
       private val totalWares = itemView.findViewById(R.id.tvTotalWare) as TextView
       private val totalPrize = itemView.findViewById(R.id.tvTotalPrize) as TextView

        fun onBind(order: Order, position: Int) {
            row.text = (position + 1).toString()

            wareName.text = order.Ware.name
            fee.text = Utils.formatCurrency(order.Ware.price.toInt())
            totalWares.text = order.count.toString()
            totalPrize.text = Utils.formatCurrency(order.Ware.price.toInt() * order.count)
        }
    }
}