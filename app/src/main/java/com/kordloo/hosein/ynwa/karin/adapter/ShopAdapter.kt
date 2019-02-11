package com.kordloo.hosein.ynwa.karin.adapter

import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.kordloo.hosein.ynwa.karin.OnShopItemListener
import com.kordloo.hosein.ynwa.karin.R
import com.kordloo.hosein.ynwa.karin.model.Ware
import com.kordloo.hosein.ynwa.karin.util.Toaster
import com.kordloo.hosein.ynwa.karin.util.Utils
import java.io.File

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.MyVH>() {

    private var list: MutableList<Ware>? = null
    private var totalWare = 0
    private var onShopItemListener: OnShopItemListener<Ware, String>? = null

    fun onShopListener(onShopItemListener: OnShopItemListener<Ware, String>) {
        this.onShopItemListener = onShopItemListener
    }

    fun setList(list: MutableList<Ware>) {
        if (list == null)
            return
        this.list?.clear()
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_shop, parent, false)
        return MyVH(view)
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.onBind(list!![position], onShopItemListener!!)
    }

    override fun getItemCount(): Int {
        return if (list == null)
            0
        else
            list?.size!!
    }


    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView), ElegantNumberButton.OnClickListener {

        private var onShopItemListener: OnShopItemListener<Ware, String>? = null
        private var ware: Ware? = null

        val cv = itemView.findViewById(R.id.cv) as CardView
        val image = itemView.findViewById(R.id.image) as ImageView
        val name = itemView.findViewById(R.id.name) as TextView
        val price = itemView.findViewById(R.id.price) as TextView
        val counter = itemView.findViewById(R.id.btnCounter) as ElegantNumberButton

        init {
            counter.setOnClickListener(this)
        }

        fun onBind(ware: Ware, onShopItemListener: OnShopItemListener<Ware, String>) {
            this.onShopItemListener = onShopItemListener
            this.ware = ware
            val imageUri = Uri.fromFile(File(ware.path))
            val size = (Utils.getWidth() / 2) - Utils.dp2px(8f)
            Utils.loadImage(imageUri, image, size)

            name.text = ware.name
            price.text = Utils.formatCurrency(ware.price.toInt())

        }
        override fun onClick(v: View?) {
            onShopItemListener?.onClick(ware!!, counter.number)
        }
    }

}