package logic.usecase.game

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.GameState
import org.berlin.model.Meal

class GuessPreparationTimeGameUseCase(
    private val mealsRepository: MealsRepository
) {
    var remainingAttempts = 1
    private var currentMeal: Meal? = null
    private var gameState = GameState.RUNNING

    fun getRandomMeal(): Meal = mealsRepository.getAllMeals().randomOrNull() ?: throw NoSuchElementException(
        "Cannot start GuessPreparationTimeGame – no meals found"
    )


    fun run() {
        currentMeal = getRandomMeal()
        remainingAttempts = 1
        gameState = GameState.RUNNING
    }

    enum class GuessResult {
        CORRECT, TOO_LOW, TOO_HIGH,
    }

    fun evaluateGuess(guess: Int): GuessResult {
        val correctTime = currentMeal?.minutes ?: 0
        return when {
            guess < correctTime -> {
                if (remainingAttempts < MAX_ATTEMPTS) remainingAttempts++
                else gameState = GameState.LOST
                GuessResult.TOO_LOW
            }

            guess > correctTime -> {
                if (remainingAttempts < MAX_ATTEMPTS) remainingAttempts++
                else gameState = GameState.LOST
                GuessResult.TOO_HIGH
            }

            else -> {
                gameState = GameState.WON
                GuessResult.CORRECT
            }
        }
    }

    fun isRunning(): Boolean {
        return gameState == GameState.RUNNING
    }

    fun getMeal(): Pair<String, Int> {
        if (currentMeal == null) throw NoSuchElementException("No such meals found")
        return Pair(currentMeal?.name ?: "", currentMeal?.minutes ?: 0)
    }

    fun getGameState() = gameState


    companion object {
        private const val MAX_ATTEMPTS = 3
    }

}
