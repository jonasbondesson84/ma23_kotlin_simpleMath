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
        val btnSettings: ImageButton = findViewById(R.id.imbSettings)
        val intentGame = Intent(this, gameActivity::class.java)
        val intentSettings = Intent(this, settingsActivity::class.java)

        val sharedPreferences = getSharedPreferences("minSharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (!sharedPreferences.contains("Name")) {
            editor.putString("Name", "Unknown")
        }
        if (!sharedPreferences.contains("Difficulty")) {
            editor.putInt("Difficulty", 1)
        }
        editor.apply()
        

        btnAddition.setOnClickListener() {
            intentGame.putExtra("method", "addition")
            startActivity(intentGame)
        }

        btnSubtraction.setOnClickListener() {
            intentGame.putExtra("method", "subtraction")
            startActivity(intentGame)
        }

        btnMultiplication.setOnClickListener() {
            intentGame.putExtra("method", "multiplication")
            startActivity(intentGame)
        }
        btnDivision.setOnClickListener() {
            intentGame.putExtra("method", "division")
            startActivity(intentGame)
        }
        btnSettings.setOnClickListener() {
            startActivity(intentSettings)
        }
    }
}