package org.berlin.presentation.game

import org.berlin.logic.InvalidInputForIngredientGameException
import org.berlin.presentation.IngredientGameInteractor
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class IngredientGameInteractorUI (
    private val ingredientGame: IngredientGameInteractor,
    private val reader: Reader,
    private val viewer: Viewer
) : UiRunner{
    override val id: Int = 11
    override val label: String = "Select the Ingredient Game"

    override fun run() {
        try {
            ingredientGame.run()
            while (ingredientGame.isRunning()) {
                viewer.display("Meal Name : ${ingredientGame.getCurrentMealName()}")
                viewer.display("Ingredients : ")
                ingredientGame.getCurrentIngredients()
                    .forEachIndexed { i, ingredient ->
                        viewer.display("${i + 1}--> $ingredient") }
                viewer.display("Choose The Number Of Correct Ingredient : ")
                ingredientGame.submitUserAnswer(
                    reader.getUserInput()?.toIntOrNull() ?: return)
                println()
            }
            viewer.display(ingredientGame.getTurnResult())
        } catch (e: InvalidInputForIngredientGameException) {
            viewer.display("${e.message}")
        } catch (e: Exception) {
            viewer.display("${e.message}")
        }
    }
}