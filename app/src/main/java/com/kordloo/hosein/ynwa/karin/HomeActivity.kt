package com.kordloo.hosein.ynwa.karin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        ivProfile.setOnClickListener(this)
        fab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            fab -> {
                Toast.makeText(this, "FAB", Toast.LENGTH_SHORT).show()
            }
            ivProfile -> {
                val i = Intent(this, ProfileActivity::class.java)
                startActivity(i)
            }
        }
    }
}
