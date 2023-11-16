package com.example.simplemath

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.postDelayed
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import kotlin.math.pow
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
    var maxNumber: Int = 0
    var highScore = mutableListOf<HighScoreUser>()
    var currentUser: String? = ""
    var haveAnswered: Boolean = false

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
        btnBack = findViewById(R.id.btnBackHighScore)
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
            if(!haveAnswered) {
                checkAnswer()
            }
        }
        btnSecondAnswer.setOnClickListener() {
            userAnswer = btnSecondAnswer.text.toString().toInt()
            if(!haveAnswered) {
                checkAnswer()
            }
        }
        btnThirdAnswer.setOnClickListener() {
            userAnswer = btnThirdAnswer.text.toString().toInt()
            if(!haveAnswered) {
                checkAnswer()
            }
        }

        btnFourthAnswer.setOnClickListener() {
            userAnswer = btnFourthAnswer.text.toString().toInt()
            if(!haveAnswered) {
                checkAnswer()
            }
        }


    }
    override fun onResume() {
        super.onResume()
        hideAddGameElements()
        tvScore.visibility = View.INVISIBLE
        tvAnswer.visibility = View.INVISIBLE
        tvCorrectAnswers.visibility = View.INVISIBLE
        tvTimer.text = getString(R.string.timeLeft, (TIMER_SECONDS/1000).toString())
        val sharedPreferences = getSharedPreferences("minSharedPref", MODE_PRIVATE)
        var difficulty = sharedPreferences.getInt("Difficulty", 1)
        currentUser = sharedPreferences.getString("Name", "Unknown")
        maxNumber = setMaxNumber(difficulty)

    }
    fun setMaxNumber(difficulty: Int): Int {
        when(method) {
            "addition", "subtraction" -> {
                return ((difficulty + 1) * 5.0.pow((difficulty + 1).toDouble()).toInt())
            }
            "multiplication" -> {
                return ((difficulty + 1) * 5)
            }
            else -> {
                return ((difficulty + 1) * 5)
            }
        }
    }

    fun startGame() {
        showAllGameElements()
        newMathProblem()
        tvTimer.visibility = View.VISIBLE
        object : CountDownTimer(TIMER_SECONDS, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                tvTimer.setText(getString(R.string.timeLeft, (millisUntilFinished / 1000).toString()))
            }

            override fun onFinish() {
                tvTimer.visibility = View.INVISIBLE
                stopGame()
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    fun newMathProblem() {
        firstNumber = Random.nextInt(1, maxNumber)
        secondNumber = Random.nextInt(1, maxNumber)

        when(method) {
            "subtraction" -> {
                firstNumber = Random.nextInt(2, maxNumber)
                secondNumber = Random.nextInt(1, firstNumber)
                correctAnswer = firstNumber - secondNumber
                tvNumbers.text = "$firstNumber-$secondNumber="
            }
            "multiplication" -> {
                correctAnswer = firstNumber * secondNumber
                tvNumbers.text = "$firstNumber*$secondNumber="
            }
            "division" -> {
                while (firstNumber % secondNumber != 0 || (firstNumber / secondNumber) > 10) {
                    firstNumber = Random.nextInt(1, maxNumber*4)
                    secondNumber = Random.nextInt(2, maxNumber)
                }
                correctAnswer = firstNumber / secondNumber
                tvNumbers.text = "$firstNumber/$secondNumber="
            }
            else -> {
                correctAnswer = firstNumber + secondNumber
                tvNumbers.text = "$firstNumber+$secondNumber="
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
        haveAnswered = true
        if(userAnswer == correctAnswer) {
            numberOfCorrectAnswers++
            tvAnswer.text = getString(R.string.correct)
            tvCorrectAnswers.visibility = View.VISIBLE

        } else {
            tvAnswer.text = getString(R.string.wrong)
        }
        tvAnswer.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        tvAnswer.startAnimation(animation)
        tvCorrectAnswers.text = getString(R.string.currentScore,numberOfCorrectAnswers.toString())
        showRightAnswer()
        Handler().postDelayed(1000) {
            btnFirstAnswer.visibility = View.VISIBLE
            btnSecondAnswer.visibility = View.VISIBLE
            btnThirdAnswer.visibility = View.VISIBLE
            btnFourthAnswer.visibility = View.VISIBLE
            newMathProblem()
            tvAnswer.visibility = View.INVISIBLE
            haveAnswered = false

        }
    }

    fun stopGame() {
        btnStart.text = getString(R.string.playAgain)
        hideAddGameElements()

        tvScore.visibility = View.VISIBLE
        tvScore.text = getString(R.string.score, numberOfCorrectAnswers.toString())
        loadHighScore()
        highScore.add(HighScoreUser(currentUser ?: "Unknown", numberOfCorrectAnswers))
        highScore.sortWith(compareByDescending { it.score })
        saveHighScore()

    }

    fun showRightAnswer() {
        when(correctAnswer) {
            btnFirstAnswer.text.toString().toInt() -> {
                btnFirstAnswer.visibility = View.VISIBLE
                btnSecondAnswer.visibility = View.INVISIBLE
                btnThirdAnswer.visibility = View.INVISIBLE
                btnFourthAnswer.visibility = View.INVISIBLE
            }
            btnSecondAnswer.text.toString().toInt() -> {
                btnFirstAnswer.visibility = View.INVISIBLE
                btnSecondAnswer.visibility = View.VISIBLE
                btnThirdAnswer.visibility = View.INVISIBLE
                btnFourthAnswer.visibility = View.INVISIBLE
            }
            btnThirdAnswer.text.toString().toInt() -> {
                btnFirstAnswer.visibility = View.INVISIBLE
                btnSecondAnswer.visibility = View.INVISIBLE
                btnThirdAnswer.visibility = View.VISIBLE
                btnFourthAnswer.visibility = View.INVISIBLE
            }
            btnFourthAnswer.text.toString().toInt() -> {
                btnFirstAnswer.visibility = View.INVISIBLE
                btnSecondAnswer.visibility = View.INVISIBLE
                btnThirdAnswer.visibility = View.INVISIBLE
                btnFourthAnswer.visibility = View.VISIBLE
            }

        }
    }

    fun saveHighScore() {


        val jsonArray = JSONArray()

        highScore.forEach { highScoreUser ->
            val highScoreJSON = JSONObject()
            highScoreJSON.put("name", highScoreUser.name)
            highScoreJSON.put("score", highScoreUser.score)


            jsonArray.put(highScoreJSON)
        }

        try {
            val filePath = this.filesDir.absolutePath + "/highscore.json"
            File(filePath).writeText(jsonArray.toString())
            Toast.makeText(this, "Det funkade. Filen sparades här: $filePath", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Det funkade ej. Fel: ${e.message}", Toast.LENGTH_SHORT).show()
        }

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


                highScore.add(HighScoreUser(name, score))
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Det gick inte att läsa in filen. Fel: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun hideAddGameElements() {
        startLayout.visibility = View.VISIBLE
        gameElement.visibility = View.INVISIBLE
        tvAnswer.visibility = View.INVISIBLE

    }
    fun showAllGameElements() {
        startLayout.visibility = View.INVISIBLE
        gameElement.visibility = View.VISIBLE

    }
}