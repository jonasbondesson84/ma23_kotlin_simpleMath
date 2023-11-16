package com.example.simplemath

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class highScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        val btnBack: Button = findViewById(R.id.btnBackHighScore)
        val rvHighScore: RecyclerView = findViewById(R.id.rvHighScore)

        btnBack.setOnClickListener() {
            finish()
        }
    }
}