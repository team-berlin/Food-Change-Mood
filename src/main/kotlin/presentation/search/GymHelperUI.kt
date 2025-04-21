package org.berlin.presentation.search

import org.berlin.Utils.DisplayMeals
import org.berlin.logic.usecase.search.GymHelperUseCase
import org.berlin.model.CaloriesAndProteinTolerance
import org.berlin.model.GymHelperInput
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class GymHelperUI (
    private val gymHelperUseCase: GymHelperUseCase,
    private val reader : Reader,
    private val viewer: Viewer ,
    private val displayMeals : DisplayMeals
) : UiRunner {
    override val id: Int = 9
    override val label: String = "Use gym helper to search for meals by calories and proteins"

    override fun run() {
        viewer.display("Please enter the number of calories:")
        val caloriesInput = reader.getUserInput()?.toDoubleOrNull()
        viewer.display("Please enter the amount of protein:")
        val proteinInput = reader.getUserInput()?.toDoubleOrNull()
        viewer.display("Enter calories tolerance if you want:")
        val caloriesToleranceInput = reader.getUserInput()?.toIntOrNull()
        viewer.display("Enter protein tolerance if you want:")
        val proteinToleranceInput = reader.getUserInput()?.toIntOrNull()

        if (caloriesInput == null || proteinInput == null) {
            viewer.display("Invalid input. Please enter numeric values.")
            return
        }

        val meals = gymHelperUseCase.getMealsByCaloriesAndProtein(
            calorieAndProteinValues = GymHelperInput(
                calories = caloriesInput,
                protein = proteinInput,
                caloriesAndProteinTolerance = CaloriesAndProteinTolerance(
                    caloriesToleranceInput ?: 30,
                    proteinToleranceInput ?: 10
                )
            ),
        )

        if (meals.isEmpty()) {
            println("No meals found matching meals.")
        } else {
            displayMeals.displayListOfMeals(meals)
        }
    }

}