package com.example.greenflag

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var email: String = ""
    private var pwd: String = ""
    private var pwd2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent

        if (intent != null && intent.hasExtra("email")
            && intent.hasExtra("pwd") && intent.hasExtra("pwd2")) {
            email = intent.getStringExtra("email")
            pwd = intent.getStringExtra("pwd")
            pwd2 = intent.getStringExtra("pwd2")
        }

        btn_create_acct.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val intent = Intent()
        intent.setClass(this, CreateAcctActivity::class.java)

        intent.putExtra("email", email)
        intent.putExtra("pwd", pwd)
        intent.putExtra("pwd2", pwd2)

        startActivity(intent)
    }
}
