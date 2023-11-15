package com.example.simplemath

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddition: ImageButton = findViewById(R.id.imbAddition)
        val btnSubtraction:ImageButton = findViewById(R.id.imbSubtraction)
        val btnMultiplication: ImageButton = findViewById(R.id.imbMultiplication)
        val btnDivision: ImageButton = findViewById(R.id.imbDivision)
        val intent = Intent(this, gameActivity::class.java)



        btnAddition.setOnClickListener() {
            intent.putExtra("method", "addition")
            startActivity(intent)
        }

        btnSubtraction.setOnClickListener() {
            intent.putExtra("method", "subtraction")
            startActivity(intent)
        }

        btnMultiplication.setOnClickListener() {
            intent.putExtra("method", "multiplication")
            startActivity(intent)
        }
        btnDivision.setOnClickListener() {
            intent.putExtra("method", "division")
            startActivity(intent)
        }
    }
}