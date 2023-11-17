package com.example.simplemath

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class settingsActivity : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var rgDifficulty: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val btnSave: Button = findViewById(R.id.btnSave)
        edtName = findViewById(R.id.edtName)
        rgDifficulty = findViewById(R.id.rgDifficulty)

        btnSave.setOnClickListener() {
            saveUser()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        //Gets what info is saved in sharedPreferences
         val sharedPreferences = getSharedPreferences("userSharedPref", MODE_PRIVATE)
        var name = sharedPreferences.getString("Name", "Unknown")
        var difficulty: Int = sharedPreferences.getInt("Difficulty", 0)
        val checkedButton: RadioButton = rgDifficulty.getChildAt(difficulty) as RadioButton
        edtName.setText(name)
        rgDifficulty.check(checkedButton.id)
    }

    fun saveUser() {
        val sharedPreferences = getSharedPreferences("userSharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Name", edtName.text.toString() ?: "unknown")
        val checkedButtonID = rgDifficulty.checkedRadioButtonId
        val difficultyNumber = rgDifficulty.indexOfChild(findViewById<RadioButton>(checkedButtonID))  //Gets difficulty by which radiobutton is checked
        editor.putInt("Difficulty", difficultyNumber ?: 0)
        editor.apply()

        Toast.makeText(this, getString(R.string.saveMessage), Toast.LENGTH_SHORT).show()
    }
}