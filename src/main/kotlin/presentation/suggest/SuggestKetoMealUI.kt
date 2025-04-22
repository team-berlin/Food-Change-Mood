package presentation.suggest;

import org.berlin.logic.usecase.suggest.SuggestKetoMealUseCase;
import org.berlin.presentation.UiRunner
import org.berlin.presentation.common.showMealDetails
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
                    showMealDetails(selectedMeal, viewer)
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

}
