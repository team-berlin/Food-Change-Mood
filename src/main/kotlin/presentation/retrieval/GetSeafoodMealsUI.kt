package org.berlin.presentation.retrieval

import org.berlin.logic.usecase.retrieval.GetSeafoodMealsUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class GetSeafoodMealsUI(
    private val seafoodMealsUseCase: GetSeafoodMealsUseCase,
    private val viewer: Viewer,
): UiRunner {
    override val id: Int = 14
    override val label: String = "Get a list of all seafood meals"

    override fun run() {
        seafoodMealsUseCase.getSeafoodMeals()
            .forEachIndexed { index, seafoodMeal ->
            viewer.display(
                "${index + 1} ,Name:${seafoodMeal.name}, has protein value:${seafoodMeal.protein}"
            )
        }
    }
}