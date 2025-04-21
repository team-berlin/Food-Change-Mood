package org.berlin.presentation.retrieval

import org.berlin.logic.usecase.suggest.SuggestItalianFoodForLargeGroupUseCase
import org.berlin.presentation.UiRunner

class SuggestItalianFoodForLargeGroupUI (
    private val suggestItalianFoodForLargeGroupUseCase:
    SuggestItalianFoodForLargeGroupUseCase
) : UiRunner {
    override val id: Int = 15
    override val label: String = "Get Italian Meals For Large Group"
    override fun run() {
        val meals = suggestItalianFoodForLargeGroupUseCase
            .suggestItalianMealsForLargeGroup()
        meals.forEach { meal ->
            println(
                "- Meal Name: ${meal.name}\n"
            )
        }
    }
}