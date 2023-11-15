package com.example.simplemath

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class gameActivity : AppCompatActivity() {

    lateinit var btnStart: Button
    lateinit var tvTimer: TextView
    lateinit var btnFirstAnswer: Button
    lateinit var btnSecondAnswer: Button
    lateinit var btnThirdAnswer: Button
    lateinit var btnFourthAnswer: Button
    lateinit var tvNumbers: TextView
    lateinit var btnBack: Button

    var userAnswer: Int = 0
    var correctAnswer: Int = 0
    var numberOfCorrectAnswers: Int = 0
    var method: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnStart = findViewById(R.id.btnStart)
        tvTimer = findViewById(R.id.tvTimer)
        btnFirstAnswer = findViewById(R.id.btnFirst)
        btnSecondAnswer = findViewById(R.id.btnSecond)
        btnThirdAnswer = findViewById(R.id.btnThird)
        btnFourthAnswer = findViewById(R.id.btnFourth)
        tvNumbers = findViewById(R.id.tvNumber)
        btnBack = findViewById(R.id.btnBack)

        method = intent.getStringExtra("method") ?: "addition"

        btnStart.setOnClickListener() {
            startGame()

        }

        btnBack.setOnClickListener() {
            finish()
        }


    }
    override fun onResume() {
        super.onResume()
        hideAddGameElements()
    }

    fun startGame() {
        showAllGameElements()
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                tvTimer.setText("Tid kvar: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                tvTimer.setText("Slut!")
                stopGame()
            }
        }.start()
    }

    fun checkAnswer() {
        if(userAnswer == correctAnswer)
            numberOfCorrectAnswers++
    }

    fun stopGame() {
        btnStart.text = "Play again"
        hideAddGameElements()


    }

    fun hideAddGameElements() {
        btnStart.visibility = View.VISIBLE
        btnBack.visibility = View.VISIBLE
        btnFirstAnswer.visibility = View.INVISIBLE
        btnSecondAnswer.visibility = View.INVISIBLE
        btnThirdAnswer.visibility = View.INVISIBLE
        btnFourthAnswer.visibility = View.INVISIBLE
        tvNumbers.visibility = View.INVISIBLE
        tvTimer.visibility = View.INVISIBLE

    }
    fun showAllGameElements() {
        btnStart.visibility = View.INVISIBLE
        btnBack.visibility = View. INVISIBLE
        btnFirstAnswer.visibility = View.VISIBLE
        btnSecondAnswer.visibility = View.VISIBLE
        btnThirdAnswer.visibility = View.VISIBLE
        btnFourthAnswer.visibility = View.VISIBLE
        tvNumbers.visibility = View.VISIBLE
        tvTimer.visibility = View.VISIBLE

    }
}