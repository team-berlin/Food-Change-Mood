package org.berlin.presentation.suggest

import org.berlin.logic.usecase.suggest.SuggestHighCalorieMealsUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.common.showMealDetails
import org.berlin.presentation.input_output.Viewer

class SuggestHighCalorieMealsUI(
    private val suggestHighCalorieMealsUseCase: SuggestHighCalorieMealsUseCase,
    private val viewer: Viewer
) : UiRunner {
    override val id: Int = 13
    override val label: String = "Do you want a suggestion for a meal with more than 700 calories"

    override fun run() {
        viewer.display("\n=== High Calorie Meal Suggestion ===")
        suggestHighCalorieMealsUseCase.suggestHighCalorieMeal().fold(
            onSuccess = { meal ->
            viewer.display("\nSuggested high-calorie meal:")
            showMealDetails(meal, viewer)
        }, onFailure = { error ->
            viewer.display("Error: ${error.message}")
        })
    }
}