package presentation.suggest;

import org.berlin.logic.usecase.suggest.SuggestKetoMealUseCase;
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader;
import org.berlin.presentation.input_output.Viewer;

class SuggestKetoMealUI(
    private val suggestKetoMealUseCase:SuggestKetoMealUseCase,
    private val viewer: Viewer,
    private val reader: Reader
): UiRunner {

    override val id: Int = 7
    override val label: String = "7 - Get friendly keto meal suggestion"

    override fun run() {
        val shuffledMeals = suggestKetoMealUseCase.suggestKetoMeal()

        if (shuffledMeals.isEmpty()) {
            viewer.display("No keto‑friendly meals available. Goodbye!")
            return
        }

        for (selectedMeal in shuffledMeals) {
            viewer.display("\n---  Keto Meal Suggestion: ---")
            viewer.display("Name: ${selectedMeal.name}")
            viewer.display("Description: ${selectedMeal.description}")
            viewer.display("---------------------------")
            viewer.display("Like it? (type 'y' for show all details or 'n' for suggest another meal or 'e' for exit)")

            when (reader.getUserInput().toString().lowercase()) {
                "y" -> {
                    showMealDetails(selectedMeal)
                    break
                }
                "n" -> {
                    viewer.display("Alright, let's try another meal.")
                    continue
                }
                "e" -> break
                else -> {
                    viewer.display("Invalid input, please try again...")
                    break
                }
            }
        }
        viewer.display("No more keto‑friendly meals available. Goodbye!")
    }

    private fun showSweetDetails(meal: Meal) {
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

    private fun showMealDetails(meal: Meal) {
        viewer.display("Name: ${meal.name}")
        viewer.display("Description: ${meal.description ?: "No description provided."}")
        viewer.display("Preparation Time (minutes): ${meal.minutes}")
        viewer.display("Tags: ${meal.tags.joinToString(", ")}")

        with(meal.nutrition) {
            viewer.display("Nutrition:")
            viewer.display("  Calories: $calories kcal")
            viewer.display("  Total Fat: $totalFat g")
            viewer.display("  Saturated Fat: $saturatedFat g")
            viewer.display("  Carbohydrates: $carbohydrates g")
            viewer.display("  Sugar: $sugar g")
            viewer.display("  Protein: $protein g")
            viewer.display("  Sodium: $sodium mg")
        }

        viewer.display("Steps:")
        meal.steps.forEachIndexed { index, step ->
            viewer.display("  ${index + 1}. $step")
        }

        viewer.display("Ingredients (${meal.nIngredients}): ${meal.ingredients.joinToString(", ")}")
    }

}
