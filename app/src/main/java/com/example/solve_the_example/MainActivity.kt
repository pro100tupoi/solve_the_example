package com.example.solve_the_example

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.solve_the_example.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var totalAnswers = 0
    private var currentAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            generateExample()
            binding.startButton.isEnabled = false
            binding.checkButton.isEnabled = true
            binding.answerEditText.isEnabled = true
            binding.answerEditText.text.clear()
            binding.mainLayout.setBackgroundColor(Color.WHITE)
        }

        binding.checkButton.setOnClickListener {
            checkAnswer()
            binding.checkButton.isEnabled = false
            binding.answerEditText.isEnabled = false
            binding.startButton.isEnabled = true
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
                binding.exampleTextView.text = "$num1 / $divisor ="
            } else {
                generateExample()
            }
        } else {
            currentAnswer = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                else -> 0
            }
            binding.exampleTextView.text = "$num1 $operator $num2 = "
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun checkAnswer() {
        val userAnswer = binding.answerEditText.text.toString().toIntOrNull() ?: Int.MIN_VALUE
        totalAnswers++

        if (userAnswer == currentAnswer) {
            correctAnswers++
            binding.mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.softGreen))
        } else {
            wrongAnswers++
            binding.mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.softRed))
        }

        updateStatistics()
    }

    private fun updateStatistics() {
        binding.Result.text = totalAnswers.toString()
        binding.ResultRight.text = correctAnswers.toString()
        binding.ResultNotRight.text = wrongAnswers.toString()

        val percentage = if (totalAnswers > 0) {
            String.format("%.2f", (correctAnswers.toDouble() / totalAnswers) * 100)
        } else {
            "0.00"
        }
        binding.PercentageOfCorrectAnswers.text = "$percentage%"
    }
}