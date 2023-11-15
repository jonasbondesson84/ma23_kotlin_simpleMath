package com.example.simplemath

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class gameActivity : AppCompatActivity() {

    lateinit var btnStart: Button
    lateinit var tvTimer: TextView
    lateinit var btnFirstAnswer: Button
    lateinit var btnSecondAnswer: Button
    lateinit var btnThirdAnswer: Button
    lateinit var btnFourthAnswer: Button
    lateinit var tvNumbers: TextView
    lateinit var btnBack: Button
    lateinit var tvCorrectAnswers: TextView
    lateinit var gameElement: ConstraintLayout
    lateinit var startLayout: ConstraintLayout
    lateinit var tvScore: TextView
    lateinit var tvAnswer: TextView

    var userAnswer: Int = 0
    var correctAnswer: Int = 0
    var numberOfCorrectAnswers: Int = 0
    var method: String = ""
    var firstNumber: Int = 0
    var secondNumber: Int = 0
    val TIMER_SECONDS: Long = 30000

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
        tvCorrectAnswers = findViewById(R.id.tvCorrectAnswers)
        gameElement = findViewById(R.id.gameElements)
        startLayout = findViewById(R.id.startLayout)
        tvScore = findViewById(R.id.tvScore)
        tvAnswer = findViewById(R.id.tvAnswer)

        method = intent.getStringExtra("method") ?: "addition"

        btnStart.setOnClickListener() {
            startGame()

        }

        btnBack.setOnClickListener() {
            finish()
        }

        btnFirstAnswer.setOnClickListener() {
            userAnswer = btnFirstAnswer.text.toString().toInt()
            checkAnswer()
        }
        btnSecondAnswer.setOnClickListener() {
            userAnswer = btnSecondAnswer.text.toString().toInt()
            checkAnswer()
        }
        btnThirdAnswer.setOnClickListener() {
            userAnswer = btnThirdAnswer.text.toString().toInt()
            checkAnswer()
        }

        btnFourthAnswer.setOnClickListener() {
            userAnswer = btnFourthAnswer.text.toString().toInt()
            checkAnswer()
        }


    }
    override fun onResume() {
        super.onResume()
        hideAddGameElements()
        tvScore.visibility = View.INVISIBLE
        tvAnswer.visibility = View.INVISIBLE
    }

    fun startGame() {
        showAllGameElements()
        newMathProblem()
        object : CountDownTimer(TIMER_SECONDS, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                tvTimer.setText("Tid kvar: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                tvTimer.setText("Slut!")
                stopGame()
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    fun newMathProblem() {
        firstNumber = Random.nextInt(1, 10)
        secondNumber = Random.nextInt(1, 10)

        when(method) {
            "subtraction" -> {
                correctAnswer = firstNumber - secondNumber
                tvNumbers.text = " $firstNumber - $secondNumber ="
            }
            "multiplication" -> {
                correctAnswer = firstNumber * secondNumber
                tvNumbers.text = " $firstNumber * $secondNumber ="
            }
            "division" -> {
                while (firstNumber % secondNumber != 0) {
                    firstNumber = Random.nextInt(1, 20)
                    secondNumber = Random.nextInt(2, 10)
                }
                correctAnswer = firstNumber / secondNumber

                tvNumbers.text = " $firstNumber / $secondNumber ="
            }
            else -> {
                correctAnswer = firstNumber + secondNumber
                tvNumbers.text = " $firstNumber + $secondNumber ="
            }
        }
        setAnswers()
    }


    fun setAnswers() {
        val answerList = mutableListOf<Int>()
        answerList.add(0, correctAnswer)
        answerList.add(1, correctAnswer+1)
        answerList.add(2, correctAnswer-1)
        answerList.add(3, correctAnswer+2)
        answerList.shuffle()
        btnFirstAnswer.text = answerList[0].toString()
        btnSecondAnswer.text = answerList[1].toString()
        btnThirdAnswer.text = answerList[2].toString()
        btnFourthAnswer.text = answerList[3].toString()
    }

    fun checkAnswer() {
        if(userAnswer == correctAnswer) {
            numberOfCorrectAnswers++
            tvAnswer.text = "Rätt!"

        } else {
            tvAnswer.text = "Fel."
        }
        tvAnswer.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        tvAnswer.startAnimation(animation)
        tvCorrectAnswers.text = "Antal rätt svar: $numberOfCorrectAnswers"
        newMathProblem()
    }

    fun stopGame() {
        btnStart.text = "Play again"
        hideAddGameElements()
        tvScore.visibility = View.VISIBLE
        tvScore.text = "Din poäng blev $numberOfCorrectAnswers!"
    }

    fun hideAddGameElements() {
        startLayout.visibility = View.VISIBLE
        gameElement.visibility = View.INVISIBLE
//        btnFirstAnswer.visibility = View.INVISIBLE
//        btnSecondAnswer.visibility = View.INVISIBLE
//        btnThirdAnswer.visibility = View.INVISIBLE
//        btnFourthAnswer.visibility = View.INVISIBLE
//        tvNumbers.visibility = View.INVISIBLE
//        tvTimer.visibility = View.INVISIBLE
//        tvCorrectAnswers.visibility = View.INVISIBLE

    }
    fun showAllGameElements() {
        startLayout.visibility = View.INVISIBLE
        gameElement.visibility = View.VISIBLE
//        btnFirstAnswer.visibility = View.VISIBLE
//        btnSecondAnswer.visibility = View.VISIBLE
//        btnThirdAnswer.visibility = View.VISIBLE
//        btnFourthAnswer.visibility = View.VISIBLE
//        tvNumbers.visibility = View.VISIBLE
//        tvTimer.visibility = View.VISIBLE
//        tvCorrectAnswers.visibility = View.VISIBLE

    }
}