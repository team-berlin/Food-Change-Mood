package org.berlin.presentation.retrieval

import org.berlin.logic.usecase.retrieval.GetIraqiMealsUseCase
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class GetIraqiMealsUI(
    private val identifyIraqiMealsUseCase: GetIraqiMealsUseCase,
    private val viewer: Viewer
): UiRunner {
    override val id: Int = 3
    override val label: String = "Identify Iraqi Meals"

    override fun run() {
        try {
            val iraqiMeals = identifyIraqiMealsUseCase.getIraqiMeals()
            viewer.display("\n--- Iraqi Meals ---")
            iraqiMeals.forEach { meal ->
                display(meal)
            }
            viewer.display("--- End of Iraqi Meals ---")
        } catch (e: NoSuchElementException) {
            viewer.display("No Iraqi meals found.")
        }
    }

    private fun display(meal: Meal){
        viewer.display("Name: ${meal.name}")
        viewer.display("ID: ${meal.id}")
        viewer.display("Description: ${meal.description ?: "No description available"}")
        viewer.display("Tags: ${meal.tags.joinToString(", ")}")
        viewer.display("Ingredients: ${meal.ingredients.joinToString(", ")}")
        viewer.display("---")
    }
}