package org.berlin.presentation
import org.berlin.logic.ExploreFoodCultureUseCase


class FoodChangeMoodUI(
    private val exploreFoodCultureUseCase: ExploreFoodCultureUseCase
) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            10 ->handleExploreFoodCulture()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun showWelcome() {
        println("Welcome to food change mood app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("10 - Explore food culture by country")
        print("Here: ")
    }

    private fun handleExploreFoodCulture() {
        print("Enter Country name:")
        readlnOrNull()?.takeIf { it.isNotBlank() }?.let { countryName ->
            try {
                val meals = exploreFoodCultureUseCase.exploreFoodByCountry(country = "")
                if (meals.isEmpty()) {
                    println(" \"$countryName\" is not found in any meal tags or descriptions.")
                } else {
                    println("\n Found ${meals.size} meals related to \"$countryName\":")
                    meals.forEachIndexed { index, meal ->
                        println("${index + 1}. ${meal.name}")
                    }
                }
            } catch (e: Exception) {
                println("️ Something went wrong while searching for \"$countryName\".")
            }
        } ?: println(" Please enter a valid country name.")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}
