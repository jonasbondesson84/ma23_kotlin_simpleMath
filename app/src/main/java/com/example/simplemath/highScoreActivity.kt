package com.example.simplemath

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.File

class highScoreActivity : AppCompatActivity() {

    var highScore = mutableListOf<HighScoreUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        val btnBack: Button = findViewById(R.id.btnBackHighScore)

        loadHighScore()


        val adapter = HighScoreAdapter(this, highScore)
        val rvHighScore: RecyclerView = findViewById(R.id.rvHighScore)
        rvHighScore.adapter = adapter
        rvHighScore.layoutManager = LinearLayoutManager(this)

        btnBack.setOnClickListener() {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        loadHighScore()


        val adapter = HighScoreAdapter(this, highScore)
        val rvHighScore: RecyclerView = findViewById(R.id.rvHighScore)
        rvHighScore.adapter = adapter
        rvHighScore.layoutManager = LinearLayoutManager(this)
        Log.d("!!!", "higscorelist består av " + highScore.size.toString())
    }

    fun loadHighScore() {
        try {
            val filePath = this.filesDir.absolutePath + "/highscore.json"
            val jsonString = File(filePath).readText()
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val highscoreJson = jsonArray.getJSONObject(i)
                val name = highscoreJson.getString("name")
                val score = highscoreJson.getInt("score")
                val difficulty = highscoreJson.getInt("difficulty")
                val method = highscoreJson.getString("method")
                val date = highscoreJson.getString("date")


                highScore.add(HighScoreUser(name, score, difficulty, method, date))
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Det gick inte att läsa in filen. Fel: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}