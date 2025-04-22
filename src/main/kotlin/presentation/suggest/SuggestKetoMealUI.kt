package presentation.suggest

import org.berlin.logic.usecase.suggest.SuggestKetoMealUseCase
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.common.showMealDetails
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class SuggestKetoMealUI(
    private val suggestKetoMealUseCase:SuggestKetoMealUseCase,
    private val viewer: Viewer,
    private val reader: Reader
): UiRunner {

    override val id: Int = 7
    override val label: String = "Get friendly keto meal suggestion"

    override fun run() {
        val shuffledMeals = suggestKetoMealUseCase.suggestKetoMeal()

        if (shuffledMeals.isEmpty()) {
            viewer.display("No keto‑friendly meals available. Goodbye!")
            return
        }

        suggestKetoMeal(shuffledMeals)
    }

    private fun suggestKetoMeal(meals: List<Meal>) {
        for (selectedMeal in meals) {
            displayMealSuggestion(selectedMeal)
            val userResponse = getUserResponse()

            if (handleUserResponse(userResponse, selectedMeal)) return
        }

        viewer.display("No more keto‑friendly meals available. Goodbye!")
    }

    private fun displayMealSuggestion(meal: Meal) {
        viewer.display("\n--- Keto Meal Suggestion ---")
        viewer.display("Name: ${meal.name}")
        viewer.display("Description: ${meal.description}")
        viewer.display("---------------------------")
        viewer.display("Like it? (type 'y' to show all details, 'n' for another meal, or 'e' to exit)")
    }

    private fun getUserResponse(): String {
        return reader.getUserInput()?.lowercase() ?: ""
    }

    private fun handleUserResponse(response: String, meal: Meal): Boolean {
        return when (response) {
            "y" -> {
                showMealDetails(meal, viewer)
                true
            }
            "n" -> {
                viewer.display("Alright, let's try another meal.")
                false
            }
            "e" -> true
            else -> {
                viewer.display("Invalid input, please try again...")
                false
            }
        }
    }
}