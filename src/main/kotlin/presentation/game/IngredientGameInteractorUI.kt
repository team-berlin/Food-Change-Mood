package org.berlin.presentation.game

import org.berlin.logic.InvalidInputForIngredientGameException
import org.berlin.presentation.IngredientGameInteractor
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.UiRunner

class IngredientGameInteractorUI (
    private val ingredientGame: IngredientGameInteractor,
    private val reader: Reader
) : UiRunner{
    override val id: Int = 11
    override val label: String = "Select the Ingredient Game"

    override fun run() {
        try {
            ingredientGame.run()
            while (ingredientGame.isRunning()) {
                println("Meal Name : ${ingredientGame.getCurrentMealName()}")
                println("Ingredients : ")
                ingredientGame.getCurrentIngredients()
                    .forEachIndexed { i, ingredient -> println("${i + 1}--> $ingredient") }
                print("Choose The Number Of Correct Ingredient : ")
                ingredientGame.submitUserAnswer(reader.getUserInput()?.toIntOrNull() ?: return)
                println()
            }
            println(ingredientGame.getTurnResult())
        } catch (e: InvalidInputForIngredientGameException) {
            println(e.message)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}