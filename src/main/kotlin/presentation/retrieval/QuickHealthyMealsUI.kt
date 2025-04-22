package org.berlin.presentation.retrieval

import org.berlin.logic.usecase.retrieval.GetQuickHealthyMealsUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.common.showMealDetails
import org.berlin.presentation.input_output.Viewer

class QuickHealthyMealsUI(
    private val quickHealthyMealsUseCase: GetQuickHealthyMealsUseCase,
    private val viewer: Viewer
) : UiRunner {
    override val id: Int = 1
    override val label: String = "Find fast healthy meals that can be prepared in 15 minutes and under"

    override fun run() {
        viewer.display("\n=== Quick & Healthy Meals ===")

        val meals = quickHealthyMealsUseCase.getQuickHealthyMeals()

        if (meals.isEmpty()) {
            viewer.display("No quick and healthy meals found.")
            return
        }
        meals.forEach { meal ->
            showMealDetails(meal, viewer)
        }
    }
}