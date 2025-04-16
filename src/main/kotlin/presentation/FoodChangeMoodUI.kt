package org.berlin.presentation

import kotlinx.datetime.LocalDate
import org.berlin.logic.SearchMealsByDateUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FoodChangeMoodUI : KoinComponent {

    private val searchMealsByDateUseCase: SearchMealsByDateUseCase by inject()

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            1 -> printFakeUseCase()
            2 -> searchMealsByDate()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun searchMealsByDate() {
        println("\n=== Search Meals by Date ===")
        println("Please enter a date in the format YYYY-MM-DD:")
        val dateInput = readLine()

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
                val mealIdInput = readLine()

                if (mealIdInput?.lowercase() != "no") {
                    try {
                        val mealId = mealIdInput?.toInt()
                        val selectedMeal = meals.find { it.id == mealId }

                        if (selectedMeal != null) {
                            displayMealDetails(selectedMeal)
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

    private fun displayMealDetails(meal: org.berlin.model.Meal) {
        println("\n=== Meal Details ===")
        println("Name: ${meal.name}")
        println("ID: ${meal.id}")
        println("Preparation Time: ${meal.minutes} minutes")
        println("Submission Date: ${meal.submissionDate}")
        println("Tags: ${meal.tags.joinToString(", ")}")
        println("Number of Steps: ${meal.nSteps}")
        println("Number of Ingredients: ${meal.nIngredients}")

        println("\nNutrition Information:")
        println("Calories: ${meal.nutrition.calories}")
        println("Total Fat: ${meal.nutrition.totalFat}g")
        println("Sugar: ${meal.nutrition.sugar}g")
        println("Sodium: ${meal.nutrition.sodium}mg")
        println("Protein: ${meal.nutrition.protein}g")
        println("Saturated Fat: ${meal.nutrition.saturatedFat}g")
        println("Carbohydrates: ${meal.nutrition.carbohydrates}g")

        if (!meal.description.isNullOrBlank()) {
            println("\nDescription:")
            println(meal.description)
        }

        println("\nIngredients:")
        meal.ingredients.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        }

        println("\nSteps:")
        meal.steps.forEachIndexed { index, step ->
            println("${index + 1}. $step")
        }
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mood app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("2 - Search Foods by Add Date")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}