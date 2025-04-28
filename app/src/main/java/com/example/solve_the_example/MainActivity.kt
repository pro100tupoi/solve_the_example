package com.example.solve_the_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var exampleTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var checkButton: Button
    private lateinit var startButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var resultRightTextView: TextView
    private lateinit var resultNotRightTextView: TextView
    private lateinit var percentageTextView: TextView
    private lateinit var mainLayout: androidx.constraintlayout.widget.ConstraintLayout

    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var totalAnswers = 0
    private var currentAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exampleTextView = findViewById(R.id.exampleTextView)
        answerEditText = findViewById(R.id.answerEditText)
        checkButton = findViewById(R.id.checkButton)
        startButton = findViewById(R.id.startButton)
        resultTextView = findViewById(R.id.Result)
        resultRightTextView = findViewById(R.id.ResultRight)
        resultNotRightTextView = findViewById(R.id.ResultNot_right)
        percentageTextView = findViewById(R.id.Percentage_of_correct_answers)
        mainLayout = findViewById(R.id.mainLayout)

        startButton.setOnClickListener {
            generateExample()
            startButton.isEnabled = false
            checkButton.isEnabled = true
            answerEditText.isEnabled = true
            answerEditText.text.clear()
            mainLayout.setBackgroundColor(Color.WHITE)
        }

        checkButton.setOnClickListener {
            checkAnswer()
            checkButton.isEnabled = false
            answerEditText.isEnabled = false
            startButton.isEnabled = true
        }
    }

    private fun generateExample() {
        val num1 = Random.nextInt(10, 100)
        val num2 = Random.nextInt(10, 100)
        val operator = when (Random.nextInt(0, 4)) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> "+"
        }

        if (operator == "/") {
            val divisors = (1..num1).filter { num1 % it == 0 && it in 10..99 }
            if (divisors.isNotEmpty()) {
                val divisor = divisors.random()
                currentAnswer = num1 / divisor
                exampleTextView.text = "$num1 / $divisor = ______"
            } else {
                generateExample() // Повторная генерация, если нет подходящих делителей
            }
        } else {
            currentAnswer = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                else -> 0
            }
            exampleTextView.text = "$num1 $operator $num2 = ______"
        }
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText.text.toString().toIntOrNull() ?: 0
        totalAnswers++

        if (userAnswer == currentAnswer) {
            correctAnswers++
            mainLayout.setBackgroundColor(Color.GREEN)
        } else {
            wrongAnswers++
            mainLayout.setBackgroundColor(Color.RED)
        }

        updateStatistics()
    }

    private fun updateStatistics() {
        resultTextView.text = totalAnswers.toString()
        resultRightTextView.text = correctAnswers.toString()
        resultNotRightTextView.text = wrongAnswers.toString()

        val percentage = if (totalAnswers > 0) {
            String.format("%.2f", (correctAnswers.toDouble() / totalAnswers) * 100)
        } else {
            "0.00"
        }
        percentageTextView.text = "$percentage%"
    }
}