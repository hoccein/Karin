package com.kordloo.hosein.ynwa.karin.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kordloo.hosein.ynwa.karin.OnItemListener
import com.kordloo.hosein.ynwa.karin.R
import com.kordloo.hosein.ynwa.karin.model.Archive
import com.kordloo.hosein.ynwa.karin.util.Utils

class ArchiveAdapter  : RecyclerView.Adapter<ArchiveAdapter.MyVH>() {

    private var list: MutableList<Archive>? = null
    private var onItemListener: OnItemListener<Archive>? = null

    fun setOnItemListener(onItemListener: OnItemListener<Archive>) {
        this.onItemListener = onItemListener
    }

    fun setList(list: MutableList<Archive>) {
        if (list == null)
            return
        this.list?.clear()
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyVH {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_archive_factor, viewGroup, false)
        return MyVH(view)
    }

    override fun onBindViewHolder(holder: ArchiveAdapter.MyVH, position: Int) {
        holder.onBind(list?.get(position)!!, onItemListener!!)
    }

    override fun getItemCount(): Int {
        return if (list == null)
            0
        else
            list?.size!!
    }


    class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var archive: Archive? = null
        private var onItemListener: OnItemListener<Archive>? = null
        var cv = itemView.findViewById(R.id.cv) as CardView
        var date = itemView.findViewById(R.id.date) as TextView
        var name = itemView.findViewById(R.id.name) as TextView
        var phone = itemView.findViewById(R.id.phone) as TextView
        var orderPrize = itemView.findViewById(R.id.orderPrize) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        fun onBind(archive: Archive, onItemListener: OnItemListener<Archive>) {
            this.archive = archive
            this.onItemListener = onItemListener

            date.text = archive.date
            name.text = archive.customerName
            phone.text = archive.customerPhone
            val prize = Utils.formatCurrency(archive.totalPrize)
            orderPrize.text = "$prize تومان"
        }

        override fun onClick(v: View?) {
            onItemListener?.onClicked(archive!!)
        }
    }
}