package logic.usecase.game


import org.berlin.logic.usecase.helper.*
import org.berlin.model.GameState
import org.berlin.model.MealForIngredientGame

class IngredientGameUseCase(
   private val randomMealsForIngredientGame: RandomMealsForIngredientGame
) {

    private var meals: List<MealForIngredientGame> = emptyList()
    private var currentIndex = 0
    private var score = 0
    private var state = GameState.RUNNING

    fun run() {
        meals = randomMealsForIngredientGame.getMeals(MAX_QUESTIONS)
        validateMealsSize()
        currentIndex = 0
        score = 0
        state = GameState.RUNNING
    }

    fun isRunning(): Boolean {
        return state == GameState.RUNNING
    }

    fun getCurrentMeal(): MealForIngredientGame {
        if (meals.isEmpty()) throw EmptyMealsException()
        return meals[currentIndex]
    }

    fun submitUserAnswer(answer: Int) {
        validateUserAnswer(answer)
        if (!isRunning()) throw IndexOutOfBoundsException("max questions $MAX_QUESTIONS")
        if (meals.isEmpty()) throw EmptyMealsException()
        updateState(fetchIngredientAnswerChosen(answer), meals[currentIndex])
    }

    fun getScore() = score

    private fun validateUserAnswer(answer: Int) {
        if (answer !in MIN_ANSWER..MAX_ANSWER) {
            throw InvalidChoiceException("Invalid Input: Only $MIN_ANSWER to $MAX_ANSWER are allowed.")
        }
    }

    private fun fetchIngredientAnswerChosen(answer: Int): String {
        return getCurrentMeal().threeIngredientOnlyOneCorrect[answer - INPUT_COMPERED_INDEX]
    }

    private fun updateState(
        ingredientAnswer: String, meal: MealForIngredientGame
    ) {
        if (ingredientAnswer == meal.correctIngredient) {
            score += QUESTION_SCORE
            currentIndex++
            if (score == MAX_SCORE) state = GameState.WON
        } else {
            state = GameState.LOST
        }
    }

    private fun validateMealsSize() {
        if (meals.size != MAX_QUESTIONS) throw MealsNotEnoughException("meals to run game is less $MAX_QUESTIONS}")
    }

    private companion object {
        const val MIN_ANSWER = 1
        const val MAX_ANSWER = 3
        const val INPUT_COMPERED_INDEX = 1
        const val MAX_QUESTIONS = 15
        const val QUESTION_SCORE = 1000
        const val MAX_SCORE = MAX_QUESTIONS * QUESTION_SCORE
    }
}