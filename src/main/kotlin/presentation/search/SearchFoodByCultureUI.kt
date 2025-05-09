package org.berlin.presentation.search

import org.berlin.logic.usecase.search.SearchFoodByCultureUseCase
import org.berlin.model.Meal
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class SearchFoodByCultureUI(
    private val exploreFoodCultureUseCase: SearchFoodByCultureUseCase,
    private val viewer: Viewer,
    private val reader :Reader

) : UiRunner {
    override val id: Int = 10
    override val label: String = "Explore food culture by country"

    override fun run() {
        viewer.display("Enter Country name:")
       val countryName = reader.getUserInput()

        if (countryName != null) {
            handleCountrySearch(countryName)
        } else {
            viewer.display("Please enter a valid country name.")
        }
    }

    private fun handleCountrySearch(countryName: String) {
        try {
            val meals = exploreFoodCultureUseCase.exploreFoodByCountry(country = countryName)
            displayMealSearchResults(countryName, meals)
        } catch (_: Exception) {
            viewer.display("️Something went wrong while searching for \"$countryName\".")
        }
    }

    private fun displayMealSearchResults(countryName: String, meals: List<Meal>) {
        if (meals.isEmpty()) {
            viewer.display("\"$countryName\" is not found in any fields.")
        } else {
            viewer.display("\nFound ${meals.size} meals related to \"$countryName\":")
            meals.forEachIndexed { index, meal ->
                viewer.display("${index + 1}. ${meal.name}")
            }
        }
    }
}
