package org.berlin.presentation.search

import kotlinx.datetime.LocalDate
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.common.showMealDetails
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class SearchMealsByDateUI(
    private val searchMealsByDateUseCase: SearchMealsByDateUseCase,
    private val reader: Reader,
    private val viewer: Viewer
): UiRunner {

    override val id: Int = 8
    override val label: String = "Search Foods by Add Date"

    //TODO separated function to get meal by id and by date
    override fun run() {
        viewer.display("\n=== Search Meals by Date ===")
        viewer.display("Please enter a date in the format YYYY-MM-DD:")
        val dateInput = reader.getUserInput()

        if (dateInput.isNullOrBlank()) {
            viewer.display("Error: Date cannot be empty.")
            return
        }

        try {
            val date = LocalDate.parse(dateInput)
            viewer.display("Successfully parsed date: $date")

            viewer.display("Fetching meals...")
            val meals = searchMealsByDateUseCase.searchMealsByDate(date)

            if (meals.isEmpty()) {
                viewer.display("No meals found for date: $date")
            } else {
                viewer.display("\nFound ${meals.size} meals added on $date:")
                meals.forEach { meal ->
                    viewer.display("ID: ${meal.id}, Name: ${meal.name}")
                }

                viewer.display("\nWould you like to see details of a specific meal? (Enter meal ID or 'no'):")
                val mealIdInput = reader.getUserInput()

                if (mealIdInput?.lowercase() != "no") {
                    try {
                        val mealId = mealIdInput?.toInt()
                        val selectedMeal = meals.find { it.id == mealId }

                        if (selectedMeal != null) {
                            showMealDetails(selectedMeal, viewer)
                        } else {
                            viewer.display("Meal with ID $mealId not found in the results.")
                        }
                    } catch (_: NumberFormatException) {
                        viewer.display("Invalid meal ID format.")
                    }
                }
            }
        } catch (e: Exception) {
            viewer.display("Error: ${e.javaClass.simpleName} - ${e.message}")
        }
    }
}