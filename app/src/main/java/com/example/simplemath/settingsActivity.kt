package com.example.simplemath

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.File

class settingsActivity : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var rgDifficulty: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val btnSave: Button = findViewById(R.id.btnSave)
        edtName = findViewById(R.id.edtName)
        rgDifficulty = findViewById(R.id.rgDifficulty)
        val sharedPreferences = getSharedPreferences("minSharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        btnSave.setOnClickListener() {

            editor.putString("Name", edtName.text.toString() ?: "unknown")
            val checkedButtonID = rgDifficulty.checkedRadioButtonId
            val difficultyNumber = rgDifficulty.indexOfChild(findViewById<RadioButton>(checkedButtonID))
            editor.putInt("Difficulty", difficultyNumber)
            editor.apply()
            saveUser(edtName.text.toString() ?: "unknown", difficultyNumber)

            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("minSharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var name = sharedPreferences.getString("Name", "Unknown")
        var difficulty: Int = sharedPreferences.getInt("Difficulty", 0)
        val checkedButton: RadioButton = rgDifficulty.getChildAt(difficulty) as RadioButton
        edtName.setText(name)
        rgDifficulty.check(checkedButton.id)
    }

    fun saveUser(name: String, difficulty: Int) {
        val json = JSONObject()
        json.put("name", name)
        json.put("Difficulty", difficulty)
        try {
            val filePath = this.filesDir.absolutePath + "/user.json"
            File(filePath).writeText(json.toString())
            Toast.makeText(this, getString(R.string.saveMessage), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.saveError), Toast.LENGTH_SHORT).show()
        }
    }
}