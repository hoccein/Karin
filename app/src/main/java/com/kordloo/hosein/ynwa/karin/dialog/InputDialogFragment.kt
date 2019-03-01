package com.kordloo.hosein.ynwa.karin.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.kordloo.hosein.ynwa.karin.OnShopItemListener
import com.kordloo.hosein.ynwa.karin.R
import com.kordloo.hosein.ynwa.karin.util.Utils
import kotlinx.android.synthetic.main.fragment_dialog_input.*

class InputDialogFragment() : DialogFragment(), View.OnClickListener {

    companion object {
        const val STATE_PROGRESS = 1
        const val STATE_NOTICE = 2
        const val STATE_DEFULT = 3
    }

    private var message: String? = null
    private var itemListenerGetInput: OnShopItemListener<Boolean, String>? = null

    fun setItemListenerInput(itemListener: OnShopItemListener<Boolean, String>) {
        this.itemListenerGetInput = itemListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.setContentView(R.layout.fragment_dialog_input)
        val width = Utils.getWidth() - Utils.dp2px(24f)

        dialog.window?.setLayout(width, /*Util.dpToPx(300)*/ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString("key_title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_input, container, false)
        isCancelable = false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnRegister.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
        state()
    }

    fun state(state: Int = STATE_DEFULT) {
        when (state) {
            STATE_DEFULT -> {
                layoutBtn.visibility = View.VISIBLE
                etInput.visibility = View.VISIBLE
                progressbar.visibility = View.GONE
                tvTitle.visibility = View.VISIBLE
                if (!TextUtils.isEmpty(message))
                    tvTitle.text = message
                else
                    tvTitle.text = "-"
            }
            STATE_NOTICE -> {
                tvTitle.visibility = View.VISIBLE
                if (!TextUtils.isEmpty(message))
                    tvTitle.text = message
                else
                    tvTitle.text = "-"

                layoutBtn.visibility = View.GONE
                etInput.visibility = View.GONE
                progressbar.visibility = View.GONE
            }
            STATE_PROGRESS -> {
                progressbar.visibility = View.VISIBLE
                layoutBtn.visibility = View.GONE
                tvTitle.visibility = View.GONE
                etInput.visibility = View.GONE
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnRegister -> {
                val input = etInput.text.toString()
                if (!TextUtils.isEmpty(input))
                    itemListenerGetInput?.onClick(true, input)
            }
            btnCancel -> {
                itemListenerGetInput?.onClick(false, "")
            }
        }
    }
}