package com.example.simplemath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
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
        val btnHighScore: Button = findViewById(R.id.btnHighScore)

        val intentGame = Intent(this, gameActivity::class.java)
        val intentSettings = Intent(this, settingsActivity::class.java)
        val intentHighScore = Intent(this, highScoreActivity::class.java)

        val sharedPreferences = getSharedPreferences("userSharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //If there isn't any user saved, you get username Unknown and difficulty 1
        if (!sharedPreferences.contains("Name") || !sharedPreferences.contains("Difficulty")) {
            editor.putString("Name", "Unknown")
            editor.putInt("Difficulty", 1)
            editor.apply()
        }

        val difficultyName: String = getDifficultyName(sharedPreferences.getInt("Difficulty", 0))

        val message = getString(R.string.loadInfo, sharedPreferences.getString("Name", "Unknown"), difficultyName)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        btnHighScore.setOnClickListener() {
            startActivity(intentHighScore)
        }

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

    fun getDifficultyName(difficultyNumber: Int):String {
        when(difficultyNumber) {
            0 -> {
                return getString(R.string.easy)
            }
            1 -> {
                return getString(R.string.medium)
            }
            else -> {
                return getString(R.string.hard)
            }
        }
    }
}