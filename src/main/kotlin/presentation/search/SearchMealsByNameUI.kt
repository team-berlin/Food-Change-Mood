package org.berlin.presentation.search

import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader

class SearchMealsByNameUI(
    private val searchMealsByNameUseCase: SearchMealsByNameUseCase,
    private val reader:Reader

): UiRunner {
    override val id: Int = 2
    override val label: String="Search meals by name"
    override fun run() {
        println("=== Search Meals by Name ===\n")
        print("Type the name or part of the meal you're looking for: ")

        val searchWord = readlnOrNull()?.trim().orEmpty()

        if (searchWord.isBlank()) {
            println("You didn't type anything. Please enter a word to search")
            return
        }

        val meals = searchMealsByNameUseCase.searchMealsByName(searchWord)

        if (meals.isEmpty()) {
            println("No meals found for \"$searchWord\".")
        } else {
            println("Found ${meals.size} result(s) for \"$searchWord\":\n")
            meals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name}")
            }
        }
    }
}