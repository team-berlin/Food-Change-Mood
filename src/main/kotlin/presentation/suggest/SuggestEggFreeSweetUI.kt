package org.berlin.presentation.suggest

import org.berlin.logic.usecase.suggest.SuggestEggFreeSweetUseCase
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class SuggestEggFreeSweetUI(
    private val suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase,
    private val viewer: Viewer,
    private val reader: Reader
): UiRunner {
    override val id: Int = 6
    override val label: String = "Suggest Egg FreeSweet"

    override fun run() {
        val suggestion = suggestEggFreeSweetUseCase.suggestEggFreeSweet()
        if (suggestion != null) {
            viewer.display("\n--- Suggested Free Sweet ---")
            viewer.display("Name: ${suggestion.name}")
            viewer.display("Description: ${suggestion.description}")
            viewer.display("---------------------------")
            viewer.display("Like it? (yes/no/exit)")
            when (reader.getUserInput()?.lowercase()) {
                "yes" -> showSweetDetails(suggestion, viewer)
                "no" -> {
                    viewer.display("Disliked. Getting another suggestion.")
                    run()
                }

                "exit" -> return
                else -> viewer.display("Invalid Input")
            }
        } else {
            println("No more egg-free sweets to suggest :( ")
        }
    }

    private fun showSweetDetails(meal: Meal, viewer: Viewer) {
        viewer.display("\n--- Sweet Details ---")
        viewer.display("Name: ${meal.name}")
        viewer.display("Description: ${meal.description}")
        viewer.display("Ingredients: ${meal.ingredients.joinToString(", ")}")
        meal.steps.let {
            viewer.display("Steps:")
            it.forEachIndexed { index, step ->
                viewer.display("${index + 1}. $step")
            }
        }
    }

}