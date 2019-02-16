package com.kordloo.hosein.ynwa.karin.adapter

import android.net.Uri
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kordloo.hosein.ynwa.karin.OnItemListener
import com.kordloo.hosein.ynwa.karin.OnShopItemListener
import com.kordloo.hosein.ynwa.karin.R
import com.kordloo.hosein.ynwa.karin.model.Ware
import com.kordloo.hosein.ynwa.karin.util.Utils
import java.io.File

open class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.MyVH>() {

    private var list: MutableList<Ware>? = null
    private var onEditListener: OnItemListener<Ware>? = null
    private var onRemoveListener: OnShopItemListener<Ware, Int>? = null

    fun setList(list: MutableList<Ware>) {
        if (list == null)
            return
        this.list?.clear()
        this.list = list
        notifyDataSetChanged()
    }

    fun remove(ware: Ware, position: Int) {
//        list?.remove(customer)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list?.size!!-1)
    }

    fun onItemListener(onItemListener: OnItemListener<Ware>) {
        this.onEditListener = onItemListener
    }

    fun onRemoveListener(onRemoveListener: OnShopItemListener<Ware, Int>) {
        this.onRemoveListener = onRemoveListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_gallery, parent, false)
        return MyVH(view)
    }

    override fun getItemCount(): Int {
        return if (list == null)
            0
        else
            list?.size!!
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.onBind(list!![position], onEditListener!!, onRemoveListener!!)
    }


    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        private var onEditListener: OnItemListener<Ware>? = null
        private var onRemoveListener: OnShopItemListener<Ware, Int>? = null
        private var ware: Ware? = null

        private val cv = itemView.findViewById(R.id.cv) as CardView
        private val image = itemView.findViewById(R.id.image) as ImageView
        private val name = itemView.findViewById(R.id.name) as TextView
        private val price = itemView.findViewById(R.id.price) as TextView
        private val edit = itemView.findViewById(R.id.edit) as TextView
        private val remove = itemView.findViewById(R.id.remove) as TextView

        init {
            edit.setOnClickListener (this)
            remove.setOnClickListener (this)
        }

        fun onBind(ware: Ware, onEditListener: OnItemListener<Ware>, onRemoveListener: OnShopItemListener<Ware, Int>) {
            this.onEditListener = onEditListener
            this.onRemoveListener = onRemoveListener
            this.ware = ware
            val imageUri = Uri.fromFile(File(ware.path))
            val size = (Utils.getWidth() / 2) - Utils.dp2px(8f)
            Utils.loadImage(imageUri, image, size)

            name.text = ware.name
            price.text = Utils.formatCurrency(ware.price.toInt())
        }

        override fun onClick(v: View?) {
           if (v == edit)
               onEditListener?.onClicked(ware!!)
            else if (v == remove)
               onRemoveListener?.onClick(ware!!, adapterPosition)
        }
    }
}