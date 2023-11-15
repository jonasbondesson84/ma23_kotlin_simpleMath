package com.example.simplemath

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class settingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val btnSave: Button = findViewById(R.id.btnSave)
        val edtName: EditText = findViewById(R.id.edtName)
        val rgDifficulty: RadioGroup = findViewById(R.id.rgDifficulty)


        btnSave.setOnClickListener() {
            val sharedPreferences = getSharedPreferences("minSharedPref", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("Name", edtName.text.toString() ?: "unknown")
            val checkedButtonID = rgDifficulty.checkedRadioButtonId
            val difficultyNumber = rgDifficulty.indexOfChild(findViewById<RadioButton>(checkedButtonID))
            editor.putInt("Difficulty", difficultyNumber)
            editor.apply()
            finish()
        }
    }
}