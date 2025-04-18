package org.berlin.presentation

import org.berlin.logic.usecase.SuggestKetoMealUseCase
import org.berlin.model.Meal
import java.util.*

class FoodChangeMoodUI(
    private val suggestKetoMealUseCase: SuggestKetoMealUseCase
) {
import org.berlin.logic.usecase.EasyFoodSuggestionUseCase

class FoodChangeMoodUI(
    private val easyFoodSuggestionRepository: EasyFoodSuggestionUseCase
) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            7 -> suggestionKetoMeal()
            1 -> printFakeUseCase()
            4 -> easyFoodSuggestion()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }
    private fun easyFoodSuggestion() {
        val meals = easyFoodSuggestionRepository.getEasyFoodSuggestion()
        meals.onSuccess {
            it.forEach { meal ->
                println(
                    """
                        ${meal.name}
                        Time: ${meal.minutes} minutes
                        Ingredients: ${meal.nIngredients}
                        Steps: ${meal.nSteps}
                        ---------------------
                    """.trimIndent()
                )
            }
        }
        meals.onFailure {
            println("No meals found")
        }
    }

    private fun suggestionKetoMeal() {
        val shuffledMeals = suggestKetoMealUseCase.suggestMeal()

        if (shuffledMeals.isEmpty()) {
            println("No keto‑friendly meals available. Goodbye!")
            return
        }

        for (selectedMeal in shuffledMeals) {
            println("\n---  Keto Meal Suggestion: ---")
            println("Name: ${selectedMeal.name}")
            println("Description: ${selectedMeal.description}")
            println("---------------------------")
            println("Like it? (type 'y' for show all details or 'n' for suggest another meal or 'e' for exit)")

            when (getStringUserInput().toString().lowercase()) {
                "y" -> {
                    showMealDetails(selectedMeal)
                    break
                }
                "n" -> {
                    println("Alright, let's try another meal.")
                    continue
                }
                "e" -> break
                else -> {
                    println("Invalid input, please try again...")
                    break
                }
            }
        }
        println("No more keto‑friendly meals available. Goodbye!")
    }

    private fun showMealDetails(meal: Meal) {
        println("\n--- Sweet Details ---")
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



    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("4 - Get easy food suggestion")
        println("7 - Get friendly keto meal suggestion")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }

    private fun getStringUserInput(): String? {
        return readlnOrNull()
    }
}
