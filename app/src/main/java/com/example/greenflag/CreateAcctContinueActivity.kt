package com.example.greenflag

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_create_acct_continue.*
import java.io.FileNotFoundException
import java.util.*

class CreateAcctContinueActivity : AppCompatActivity() {

    //private var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acct_continue)

        var age = 0

        btn_dob.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()

            val day:Int = calendar.get(Calendar.DAY_OF_MONTH)
            val mMonth:Int = calendar.get(Calendar.MONTH)
            val mYear:Int = calendar.get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    btn_dob.text = "${month + 1}/$dayOfMonth/$year"
                    age = mYear - year
                    if(mMonth > month) age -= age
                    if(mMonth == month){
                        if(day < dayOfMonth) age -= age
                    }
                }, mMonth, day, mYear)

            datePickerDialog.show()

        }
        btn_dob.setOnFocusChangeListener { v, hasFocus -> et_age.setText("$age") }

        //et_age.setText("$age")

        spn_country.prompt = getString(R.string.spinner_country_title)

        val arrAdapter:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
            R.array.countries, R.layout.support_simple_spinner_dropdown_item)
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spn_country.adapter = arrAdapter

        btn_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //intent.action = Intent.ACTION_PICK
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            val uri:Uri = data!!.data
            val bitmap:Bitmap

            try{
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                ic_photo.setImageBitmap(bitmap)
            }catch (e: Exception){ //FileNotFound
                e.printStackTrace()
            }
        }
    }
}
