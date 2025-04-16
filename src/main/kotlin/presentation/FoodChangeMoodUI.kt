package org.berlin.presentation

import org.berlin.logic.usecase.HighCalorieMeals
import org.berlin.model.Meal

class FoodChangeMoodUI(private val highCalorieMeals: HighCalorieMeals) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            13 -> suggestHighCalorieMeal()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun suggestHighCalorieMeal() {
        println("\n=== High Calorie Meal Suggestion ===")

        highCalorieMeals.suggestHighCalorieMeal()
            .fold(
                onSuccess = { meal ->
                    println("\nSuggested high-calorie meal:")
                    displayMeal(meal)
                },
                onFailure = { error ->
                    println("Error: ${error.message}")
                }
            )
    }

    private fun displayMeal(meal: Meal) {
        println(meal.name)
        println("    Preparation Time: ${meal.minutes} minutes")
        println("    Tags: ${meal.tags.joinToString(", ")}")
        println("    Nutrition:")
        println("      - Calories: ${meal.nutrition.calories}")
        println("      - Protein: ${meal.nutrition.protein}g")
        println("      - Total Fat: ${meal.nutrition.totalFat}g")
        println("      - Saturated Fat: ${meal.nutrition.saturatedFat}g")
        println("      - Carbohydrates: ${meal.nutrition.carbohydrates}g")
        println("      - Sugar: ${meal.nutrition.sugar}g")
        println("      - Sodium: ${meal.nutrition.sodium}mg")
        println("    Ingredients: ${meal.nIngredients}")
        println("    Steps: ${meal.nSteps}")
    }


    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("13 - Do you want a suggestion for a meal with more than 700 calories")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}
