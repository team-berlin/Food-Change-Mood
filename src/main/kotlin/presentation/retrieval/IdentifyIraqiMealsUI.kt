package org.berlin.presentation.retrieval

import org.berlin.logic.usecase.retrieval.IdentifyIraqiMealsUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class IdentifyIraqiMealsUI(
    private val identifyIraqiMealsUseCase: IdentifyIraqiMealsUseCase,
    private val viewer: Viewer
): UiRunner {
    override val id: Int = 3
    override val label: String = "Identify Iraqi Meals"

    override fun run() {
        val iraqiMeals = identifyIraqiMealsUseCase.identifyIraqiMeals()
        if (iraqiMeals.isNotEmpty()) {
            viewer.display("\n--- Iraqi Meals ---")
            iraqiMeals.forEach { meal ->
                viewer.display("Name: ${meal.name}")
                viewer.display("ID: ${meal.id}")
                viewer.display("Description: ${meal.description ?: "No description available"}")
                viewer.display("Tags: ${meal.tags.joinToString(", ")}")
                viewer.display("Ingredients: ${meal.ingredients.joinToString(", ")}")
                viewer.display("---")
            }
            viewer.display("--- End of Iraqi Meals ---")
        } else {
            viewer.display("No Iraqi meals found.")
        }
    }
}