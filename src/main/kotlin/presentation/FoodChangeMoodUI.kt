package org.berlin.presentation

import org.berlin.logic.usecase.QuickHealthyMeals

class FoodChangeMoodUI(private val quickHealthyMeals: QuickHealthyMeals) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            1 -> displayQuickHealthyMeals()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun displayQuickHealthyMeals() {
        println("\n=== Quick & Healthy Meals ===")

        val meals = quickHealthyMeals.getQuickHealthyMeals()

        if (meals.isEmpty()) {
            println("No quick and healthy meals found.")
            return
        }

        meals.forEachIndexed { index, meal ->
            println("\n[${index + 1}] ${meal.name}")
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

        println("\nTotal meals found: ${meals.size}")
    }

    private fun showWelcome() {
        println("Welcome to food change mood app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Find fast healthy meals that can be prepared in 15 minutes and under")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
