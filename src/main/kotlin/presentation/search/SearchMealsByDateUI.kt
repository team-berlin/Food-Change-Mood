package org.berlin.presentation.search

import kotlinx.datetime.LocalDate
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader

class SearchMealsByDateUI(
    private val searchMealsByDateUseCase: SearchMealsByDateUseCase,
    private val reader: Reader
): UiRunner {

    override val id: Int = 8
    override val label: String = "Search Foods by Add Date"

    //TODO separated function to get meal by id and by date
    override fun run() {
        println("\n=== Search Meals by Date ===")
        println("Please enter a date in the format YYYY-MM-DD:")
        val dateInput = reader.getUserInput()

        if (dateInput.isNullOrBlank()) {
            println("Error: Date cannot be empty.")
            return
        }

        try {
            val date = LocalDate.parse(dateInput)
            println("Successfully parsed date: $date")

            println("Fetching meals...")
            val meals = searchMealsByDateUseCase.searchMealsByDate(date)

            if (meals.isEmpty()) {
                println("No meals found for date: $date")
            } else {
                println("\nFound ${meals.size} meals added on $date:")
                meals.forEach { meal ->
                    println("ID: ${meal.id}, Name: ${meal.name}")
                }

                println("\nWould you like to see details of a specific meal? (Enter meal ID or 'no'):")
                val mealIdInput = reader.getUserInput()

                if (mealIdInput?.lowercase() != "no") {
                    try {
                        val mealId = mealIdInput?.toInt()
                        val selectedMeal = meals.find { it.id == mealId }

                        if (selectedMeal != null) {
                            showMealDetails(selectedMeal)
                        } else {
                            println("Meal with ID $mealId not found in the results.")
                        }
                    } catch (e: NumberFormatException) {
                        println("Invalid meal ID format.")
                    }
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.javaClass.simpleName} - ${e.message}")
        }
    }

    private fun showMealDetails(meal: Meal) {
        println("Name: ${meal.name}")
        println("Description: ${meal.description ?: "No description provided."}")
        println("Preparation Time (minutes): ${meal.minutes}")
        println("Tags: ${meal.tags.joinToString(", ")}")

        with(meal.nutrition) {
            println("Nutrition:")
            println("  Calories: $calories kcal")
            println("  Total Fat: $totalFat g")
            println("  Saturated Fat: $saturatedFat g")
            println("  Carbohydrates: $carbohydrates g")
            println("  Sugar: $sugar g")
            println("  Protein: $protein g")
            println("  Sodium: $sodium mg")
        }

        println("Steps:")
        meal.steps.forEachIndexed { index, step ->
            println("  ${index + 1}. $step")
        }

        println("Ingredients (${meal.nIngredients}): ${meal.ingredients.joinToString(", ")}")
    }

}