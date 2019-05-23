package com.example.greenflag

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_create_acct.*
import java.util.regex.Pattern

class CreateAcctActivity : AppCompatActivity(), TextWatcher {

    private var email = ""
    private var pwd: String = ""
    private var pwd2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acct)

        //var email = et_email.text.toString().trim()
        pwd = et_pwd.text.toString()
        pwd2 = et_pwd_confirm.text.toString()

        iv_arrow_left.setOnClickListener { goBackHome() }

        et_email.setOnFocusChangeListener { v, hasFocus ->
            email = et_email.text.toString().trim()
            isEmailValid(email)
            checkForm()
        }
        et_pwd.setOnFocusChangeListener { v, hasFocus ->
            pwd = et_pwd.text.toString()
            checkForm()
        }
        et_pwd_confirm.setOnFocusChangeListener { v, hasFocus ->
            pwd2 = et_pwd_confirm.text.toString()
            checkForm()
        }
        et_pwd_confirm.setOnClickListener {
            pwd2 = et_pwd_confirm.text.toString()
            checkForm()
        }
        et_pwd_confirm.addTextChangedListener(this)

        btn_next.setOnClickListener {
            nextStep()
        }
    }

    /*override fun onBackPressed() {
        super.onBackPressed()
    }*/

    private fun goBackHome() {
        val intent = Intent()

        intent.setClass(applicationContext, MainActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("pwd", pwd)
        intent.putExtra("pwd2", pwd2)

        startActivity(intent)

    }

    private fun nextStep(){
        val intent = Intent(this, CreateAcctContinueActivity::class.java)
        email = et_email.text.toString().trim()
        pwd = et_pwd.text.toString()
        pwd2 = et_pwd_confirm.text.toString()
        if(isEmailValid(email) && isPasswordValid(pwd) && isPasswordConfirmValid(pwd2))
            startActivity(intent)
        else return
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        val myMatcher = pattern.matcher(email)

        //Toast.makeText(this, "Email clicked: $email", Toast.LENGTH_SHORT).show()

        if (email.isNotEmpty()) {
            if (myMatcher.find()) {
                et_email.setBackgroundResource(R.drawable.border_color_good)
                iv_check_email.visibility = View.VISIBLE

                return true
            }
            et_email.setBackgroundResource(R.drawable.border_color_bad)
            iv_check_email.visibility = View.GONE

            return false

        } else {
            et_email.setBackgroundResource(R.drawable.border_none)
            iv_check_email.visibility = View.GONE

            btn_next.isEnabled = false

            return false
        }
    }

    private fun isPasswordValid(password: String): Boolean {

        val pwdRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
        val pattern = Pattern.compile(pwdRegex)
        val myMatcher = pattern.matcher(password)

        if (password.length < 8 && password.isNotEmpty()) {
            btn_next.isEnabled = false
            et_pwd.setBackgroundResource(R.drawable.border_color_bad)
            iv_check_pwd.visibility = View.GONE

            return false
        } else {
            if (myMatcher.find()) {
                //if(isPasswordConfirmValid(pwd2)) btn_next.isEnabled = true
                et_pwd.setBackgroundResource(R.drawable.border_color_good)
                iv_check_pwd.visibility = View.VISIBLE
                return true
            } else {
                btn_next.isEnabled = false
                et_pwd.setBackgroundResource(R.drawable.border_color_bad)
                iv_check_pwd.visibility = View.GONE

                return false
            }
        }
    }

    private fun isPasswordConfirmValid(passwordConf: String): Boolean {

        if (isPasswordValid(passwordConf) && passwordConf == pwd) {
            et_pwd_confirm.setBackgroundResource(R.drawable.border_color_good)
            iv_check_pwd_conf.visibility = View.VISIBLE

            return true
        } else if (passwordConf != pwd) {
            et_pwd_confirm.setBackgroundResource(R.drawable.border_color_bad)
            iv_check_pwd_conf.visibility = View.GONE

            return false
        } else {
            et_pwd_confirm.setBackgroundResource(R.drawable.border_color_bad)
            iv_check_pwd_conf.visibility = View.GONE

            return false
        }
    }

    private fun checkForm() {
        btn_next.isEnabled = false

        if (isEmailValid(email) && isPasswordValid(pwd) && isPasswordConfirmValid(pwd2)) {
            btn_next.isEnabled = true
        }
    }

    override fun afterTextChanged(s: Editable?) {
        checkForm()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //To change body of created functions use File | Settings | File Templates.
        checkForm()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        checkForm()
    }
}
