package org.berlin.presentation.search

import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class SearchMealsByNameUI(
    private val searchMealsByNameUseCase: SearchMealsByNameUseCase,
    private val reader: Reader,
    private val viewer: Viewer,

    ) : UiRunner {
    override val id: Int = 2
    override val label: String = "Search meals by name"
    override fun run() {
        viewer.display("=== Search Meals by Name ===\n")
        viewer.display("Type the name or part of the meal you're looking for: ")

        val searchWord = readlnOrNull()?.trim().orEmpty()

        if (searchWord.isBlank()) {
            viewer.display("You didn't type anything. Please enter a word to search")
            return
        }

        val meals = searchMealsByNameUseCase.searchMealsByName(
            searchWord
        )

        if (meals.isEmpty()) {
            viewer.display("No meals found for \"$searchWord\".")
        } else {
            viewer.display("Found ${meals.size} result(s) for \"$searchWord\":\n")
            meals.forEach { meal ->
                viewer.display(meal.name)
            }
        }
    }
}