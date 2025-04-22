package org.berlin.presentation.suggest

import org.berlin.logic.usecase.suggest.SuggestItalianFoodForLargeGroupUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class SuggestItalianFoodForLargeGroupUI (
    private val suggestItalianFoodForLargeGroupUseCase:
    SuggestItalianFoodForLargeGroupUseCase,
    private val viewer: Viewer
) : UiRunner {
    override val id: Int = 15
    override val label: String = "Get Italian Meals For Large Group"

    override fun run() {
        val meals = suggestItalianFoodForLargeGroupUseCase
            .suggestItalianMealsForLargeGroup()
        meals.forEach { meal ->
            viewer.display(
                "- Meal Name: ${meal.name}\n"
            )
        }
    }
}