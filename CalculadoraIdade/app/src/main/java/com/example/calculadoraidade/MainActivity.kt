package com.example.calculadoraidade

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvDateSelectedDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvDateSelectedDate = findViewById(R.id.tvSelectDate)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            { view, selectYear, selectMonth, selectDayOfMonth ->
                Toast.makeText(
                    this,
                    "Year $selectYear - Month ${selectMonth + 1} - Day $selectDayOfMonth",
                    Toast.LENGTH_LONG
                ).show()
                val selectedDate = "$selectDayOfMonth/${selectMonth + 1}/$selectYear"
                tvDateSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd//MM/yyyy",Locale.ENGLISH)

                val date = sdf.parse(selectedDate)
            },
            year, month, day

        ).show()


    }
}