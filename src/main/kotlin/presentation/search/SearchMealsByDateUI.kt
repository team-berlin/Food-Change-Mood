package org.berlin.presentation.search

import kotlinx.datetime.LocalDate
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.model.Meal
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

    override fun run() {
        viewer.display("\n=== Search Meals by Date ===")
        val dateInput = getUserDateInput() ?: return

        try {
            val date = parseDate(dateInput)
            val meals = fetchMealsByDate(date)

            if (meals.isEmpty()) {
                viewer.display("No meals found for date: $date")
            } else {
                displayMealList(date, meals)
                askForMealDetails(meals)
            }
        } catch (e: Exception) {
            viewer.display("Error: ${e.javaClass.simpleName} - ${e.message}")
        }
    }

    private fun getUserDateInput(): String? {
        viewer.display("Please enter a date in the format YYYY-MM-DD:")
        val input = reader.getUserInput()

        if (input.isNullOrBlank()) {
            viewer.display("Error: Date cannot be empty.")
            return null
        }
        return input
    }

    private fun parseDate(dateInput: String): LocalDate {
        return LocalDate.parse(dateInput).also {
            viewer.display("Successfully parsed date: $it")
        }
    }

    private fun fetchMealsByDate(date: LocalDate): List<Meal> {
        viewer.display("Fetching meals...")
        return searchMealsByDateUseCase.searchMealsByDate(date)
    }

    private fun displayMealList(date: LocalDate, meals: List<Meal>) {
        viewer.display("\nFound ${meals.size} meals added on $date:")
        meals.forEach { meal ->
            viewer.display("ID: ${meal.id}, Name: ${meal.name}")
        }
    }

    private fun askForMealDetails(meals: List<Meal>) {
        viewer.display("\nWould you like to see details of a specific meal? (Enter meal ID or 'no'):")
        val mealIdInput = reader.getUserInput()

        if (mealIdInput?.lowercase() == "no") return

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