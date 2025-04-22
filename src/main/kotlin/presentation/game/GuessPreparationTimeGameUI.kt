package org.berlin.presentation.game

import logic.usecase.game.GuessPreparationTimeGameUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class GuessPreparationTimeGameUI(
    private val guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase,
    private val reader: Reader,
    private val viewer: Viewer
): UiRunner {

    override val id: Int = 5
    override val label: String = "Guess Preparation Time Game"

    override fun run() {
        val randomMeal = guessPreparationTimeGameUseCase.getRandomMeal()
        val mealName = randomMeal.name
        val correctPreparationTime = randomMeal.minutes

        playGame(mealName, correctPreparationTime)
    }

    private fun playGame(mealName: String, correctPreparationTime: Int) {
        var remainingAttempts = MAX_ATTEMPTS
        var isCorrect = false

        viewer.display("Guess the preparation time of $mealName meal")

        while (remainingAttempts > 0) {
            val userGuess = getUserGuess()

            if (userGuess == null) {
                viewer.display("Please enter a valid input")
                continue
            }

            isCorrect = processGuess(userGuess, correctPreparationTime)

            if (isCorrect) break

            remainingAttempts--
        }

        showFinalResult(isCorrect, mealName, correctPreparationTime)
    }

    private fun getUserGuess(): Int? =
        try {
            reader.getUserInput()?.toIntOrNull()
        } catch (_: Exception) {
            null
        }

    private fun processGuess(guess: Int, correctTime: Int): Boolean {
        return when {
            guess < correctTime -> {
                viewer.display("Too low, try again")
                false
            }
            guess > correctTime -> {
                viewer.display("Too high, try again")
                false
            }
            else -> {
                viewer.display("Great Job! It takes $correctTime minutes")
                true
            }
        }
    }

    private fun showFinalResult(isCorrect: Boolean, mealName: String, correctTime: Int) {
        if (!isCorrect) {
            viewer.display("The time it takes to prepare $mealName meal is $correctTime minutes")
        }
    }

    companion object {
        private const val MAX_ATTEMPTS = 3
    }
}