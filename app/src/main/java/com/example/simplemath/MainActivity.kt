package com.example.simplemath

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File

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
        try {
            val filePath = this.filesDir.absolutePath + "/user.json"
            val jsonString = File(filePath).readText()

            val loadedJson = JSONObject(jsonString)

            val name = loadedJson.getString("name")
            val difficulty = loadedJson.getInt("Difficulty")


            val sharedPreferences = getSharedPreferences("minSharedPref", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("Name", name)
            editor.putInt("Difficulty", difficulty)
            editor.apply()

            val message = getString(R.string.loadInfo, name, difficulty.toString())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.loadError), Toast.LENGTH_SHORT).show()
        }
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