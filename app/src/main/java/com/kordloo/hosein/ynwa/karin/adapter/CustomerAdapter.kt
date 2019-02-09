package com.kordloo.hosein.ynwa.karin.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kordloo.hosein.ynwa.karin.OnItemListener
import com.kordloo.hosein.ynwa.karin.OnLongItemListener
import com.kordloo.hosein.ynwa.karin.R
import com.kordloo.hosein.ynwa.karin.model.Customer

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.MyVH>() {

    private var list: MutableList<Customer>? = null
    private var onItemListener: OnItemListener<Customer>? = null
    private var onLongItemListener: OnLongItemListener<Customer, Int>? = null

    fun setOnItemListener(onItemListener: OnItemListener<Customer>) {
        this.onItemListener = onItemListener
    }

    fun setOnLongItemListener(onLongItemListener: OnLongItemListener<Customer, Int>) {
        this.onLongItemListener = onLongItemListener
    }

    fun setList(list: MutableList<Customer>) {
        if (list == null)
            return
        this.list?.clear()
        this.list = list
        notifyDataSetChanged()
    }

    fun addCustomer(customer: Customer) {
        list?.add(customer)
        notifyItemInserted(list?.size!!-1)
    }

    fun remove(customer: Customer, position: Int) {
//        list?.remove(customer)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list?.size!!-1)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyVH {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_customer, viewGroup, false)
        return MyVH(view)
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.onBind(list?.get(position)!!, onItemListener!!, onLongItemListener!!)
    }

    override fun getItemCount(): Int {
        return if (list == null)
            0
        else
            list?.size!!
    }


    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        private var customer: Customer? = null
        private var onItemListener: OnItemListener<Customer>? = null
        private var onLongItemListener: OnLongItemListener<Customer, Int>? = null
        var cv = itemView.findViewById(R.id.cv) as CardView
        var name = itemView.findViewById(R.id.name) as TextView
        var phone = itemView.findViewById(R.id.phone) as TextView
        var address = itemView.findViewById(R.id.address) as TextView

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun onBind(customer: Customer, onItemListener: OnItemListener<Customer>,
                   onLongItemListener: OnLongItemListener<Customer, Int>) {
            this.customer = customer
            this.onItemListener = onItemListener
            this.onLongItemListener = onLongItemListener

            name.text = customer.name
            phone.text = customer.phone
            if (!TextUtils.isEmpty(customer.address)) {
                address.visibility = View.VISIBLE
                address.text = customer.address
            }
            else
                address.visibility = View.GONE
        }

        override fun onClick(v: View?) {
            onItemListener?.onClicked(customer!!)
        }

        override fun onLongClick(v: View?): Boolean {
            onLongItemListener?.onClicked(customer!!, adapterPosition)
            return true
        }
    }
}