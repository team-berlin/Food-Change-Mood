package org.berlin.presentation.suggest

import org.berlin.logic.usecase.suggest.SuggestEasyFoodUseCase
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class SuggestionEasyFoodUI(
    private val suggestEasyFoodUseCase: SuggestEasyFoodUseCase,
    private val viewer: Viewer
) : UiRunner {
    override val id: Int = 4
    override val label: String = "Get 10 random meals that are east to prepare"

    override fun run() {
        suggestEasyFoodUseCase.getEasyFoodSuggestion().forEach { meal ->
            displayEasyFoodMeal(meal, viewer)
        }
    }

    private fun displayEasyFoodMeal(meal: Meal, viewer: Viewer) {
        viewer.display(
            """
                    ${meal.name}
                    Time: ${meal.minutes} minutes
                    Ingredients: ${meal.numberOfIngredients}
                    Steps: ${meal.numberOfSteps}
                    ---------------------
                """.trimIndent()
        )
    }
}