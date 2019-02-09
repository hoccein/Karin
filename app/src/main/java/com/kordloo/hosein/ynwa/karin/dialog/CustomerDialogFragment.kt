package com.kordloo.hosein.ynwa.karin.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kordloo.hosein.ynwa.karin.OnItemListener
import com.kordloo.hosein.ynwa.karin.R
import com.kordloo.hosein.ynwa.karin.event.CustomerEvent
import com.kordloo.hosein.ynwa.karin.model.Customer
import com.kordloo.hosein.ynwa.karin.util.Toaster
import com.kordloo.hosein.ynwa.karin.util.Utils
import kotlinx.android.synthetic.main.dialog_frag_customer.*
import org.greenrobot.eventbus.EventBus

class CustomerDialogFragment : DialogFragment() {

    private var onItemListener: OnItemListener<Customer>? = null

    fun clickOnItemListener(onItemListener: OnItemListener<Customer>) {
        this.onItemListener = onItemListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(STYLE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_frag_customer)
        dialog.show()
        return dialog
    }

    override fun onResume() {
        super.onResume()
        val params = dialog.window?.attributes
        val screenWidth = Utils.getWidth()
        params?.width = (screenWidth * 4) / 5
        params?.height = screenWidth
        dialog.window?.attributes = params as android.view.WindowManager.LayoutParams
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val v = inflater.inflate(R.layout.dialog_frag_customer, container, false)
        isCancelable = false
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cancel.setOnClickListener {
            dismiss()
        }

        add.setOnClickListener {
            val cName = name.text.toString()
            if (TextUtils.isEmpty(cName)) {
                Toaster.show(getString(R.string.errorName))
                return@setOnClickListener
            }
            val cPhone = phone.text.toString()
            if (TextUtils.isEmpty(cPhone)) {
                Toaster.show(getString(R.string.errorPhone))
                return@setOnClickListener
            }
            val cAddress = address.text.toString()
            val customer = Customer(name = cName, phone = cPhone, address = cAddress)
            EventBus.getDefault().post(CustomerEvent(customer))
            dismiss()
        }
    }
}