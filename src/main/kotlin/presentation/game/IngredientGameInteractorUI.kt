package org.berlin.presentation.game

import org.berlin.model.MealForIngredientGame
import org.berlin.presentation.IngredientGameInteractor
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class IngredientGameInteractorUI(
    private val ingredientGame: IngredientGameInteractor,
    private val reader: Reader,
    private val viewer: Viewer
): UiRunner {
    override val id: Int = 11
    override val label: String = "Ingredient Game: Select the Only One True Ingredient"

    override fun run() {
        try {
            startTurn()
        } catch (e: Exception) {
            viewer.display("${e.message}")
        }
    }

    private fun startTurn() {
        ingredientGame.run()
        while (ingredientGame.isRunning()) {
            displayMealInfo(ingredientGame.getCurrentMeal())
            val userAnswer = getMatchedUserInput()
            if (userAnswer == EXIT_OPTION) return
            ingredientGame.submitUserAnswer(userAnswer)
            println()
        }
        viewer.display("Game end Score: ${ingredientGame.getScore()}")
    }

    private fun displayMealInfo(meal: MealForIngredientGame) {
        viewer.display("Meal Name : ${meal.mealName}")
        viewer.display("Ingredients : ")
        meal.threeIngredientOnlyOneCorrect.forEachIndexed { index, ingredient ->
                viewer.display("${index + 1} --> $ingredient")
            }
    }

    private fun getMatchedUserInput(): Int {
        viewer.display("Choose The Number Of Correct Ingredient or 0 To Exit(Ingredient Game) : ")
        val input: Int = reader.getUserInput()?.toIntOrNull() ?: getMatchedUserInput()
        if (input in EXIT_OPTION..MAX_CHOICE) return input

        return getMatchedUserInput()
    }

    private companion object {
        const val EXIT_OPTION = 0
        const val MAX_CHOICE = 3
    }
}