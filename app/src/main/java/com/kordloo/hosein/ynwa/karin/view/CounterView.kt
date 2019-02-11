package com.kordloo.hosein.ynwa.karin.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.kordloo.hosein.ynwa.karin.R
import kotlinx.android.synthetic.main.view_counter.view.*

class CounterView : LinearLayout, View.OnClickListener{

    private val MIN = 0
    private val MAX = 100
    private var count = MIN

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.view_counter, this, true)
//        orientation = HORIZONTAL
//        gravity = Gravity.CENTER
//        setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
//        ivDec.setOnClickListener(this)
//        ivInc.setOnClickListener(this)
    }

    fun setCount(num: Int) {
        count = num
        tvCount.text = count.toString()
    }

    override fun onClick(v: View?) {
        when (v) {
            ivInc -> {
                if (count > MIN) {
                    count--
                    tvCount.text = count.toString()
                }
            }
            ivDec -> {
                if (count < MAX) {
                    count++
                    tvCount.text = count.toString()
                }
            }
        }
    }
}