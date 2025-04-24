package org.berlin.presentation.search

import org.berlin.logic.usecase.search.SearchGymFriendlyMealsUseCase
import org.berlin.model.CaloriesAndProteinTolerance
import org.berlin.model.GymHelperInput
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.common.showMealDetails
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class SearchGymFriendlyMealsUI(
    private val gymHelperUseCase: SearchGymFriendlyMealsUseCase,
    private val reader: Reader,
    private val viewer: Viewer,
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

        try {
            val meals = gymHelperUseCase.getMealsByCaloriesAndProtein(
                input = GymHelperInput(
                    calories = caloriesInput,
                    protein = proteinInput,
                    caloriesAndProteinTolerance = CaloriesAndProteinTolerance(
                        caloriesToleranceInput ?: 30, proteinToleranceInput ?: 10
                    )
                ),
            )
            displayListOfMeals(meals, viewer)
        }
        catch (e : NoSuchElementException) {
            viewer.display("No meals found matching meals.")
        }

    }

    private fun displayListOfMeals(meals: List<Meal>, viewer: Viewer) {
        meals.forEach { meal ->
            viewer.display("\n- ${meal.name}")
            showMealDetails(meal, viewer)
        }
        viewer.display("\nTotal meals found: ${meals.size}")
    }
}