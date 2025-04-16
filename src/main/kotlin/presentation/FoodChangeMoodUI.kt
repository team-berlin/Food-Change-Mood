package org.berlin.presentation

import org.berlin.logic.IdentifyIraqiMealsUseCase
import org.berlin.logic.SuggestEggFreeSweetUseCase
import org.berlin.model.Meal


class FoodChangeMoodUI(
    private val identifyIraqiMealsUseCase : IdentifyIraqiMealsUseCase,
    private val suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase
) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            1 -> printFakeUseCase()
            2 -> identifyIraqiMeals()
            3 -> suggestEggFreeSweet()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun suggestEggFreeSweet() {
        val suggestion = suggestEggFreeSweetUseCase.suggestEggFreeSweet()
        if (suggestion != null) {
            println("\n--- Suggested Free Sweet ---")
            println("Name: ${suggestion.name}")
            println("Description: ${suggestion.description}")
            println("---------------------------")
            println("Like it? (yes/no/exit)")
            when(readLine()?.lowercase()){
                "yes" -> showSweetDetails(suggestion)
                "no" -> {
                    println("Disliked. Getting another suggestion.")
                    suggestEggFreeSweet()
                }
                "exit" -> presentFeatures()
                else -> println("Invalid Input")
            }
        }else{
            println("No more egg-free sweets to suggest :( ")
        }
    }

    private fun showSweetDetails(meal : Meal) {
        println("\n--- Sweet Details ---")
        println("Name: ${meal.name}")
        println("Description: ${meal.description}")
        println("Ingredients: ${meal.ingredients?.joinToString(", ")}")
        meal.steps.let {
            println("Steps:")
            it?.forEachIndexed { index, step ->
                println("${index + 1}. $step")
            }
        }
    }

    private fun identifyIraqiMeals() {
        println(identifyIraqiMealsUseCase.identifyIraqiMeals().toString())
    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("2 - Identify Iraqi Meals")
        println("3 - Suggest Egg FreeSweet")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
