package com.example.tipcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipCalculator = findViewById<Button>(R.id.tipCalculator)
        val tipTime = findViewById<Button>(R.id.tipTime)

        tipCalculator.setOnClickListener {
            val intent = Intent(this, TipCalculatorActivity::class.java)
            startActivity(intent)
        }

        tipTime.setOnClickListener {
            val intent = Intent(this, TipTimeActivity::class.java)
            startActivity(intent)
        }

    }
}