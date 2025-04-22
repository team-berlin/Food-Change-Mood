package org.berlin.presentation.search

import org.berlin.logic.usecase.search.ExploreFoodCultureUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class ExploreFoodCultureUI(
    private val exploreFoodCultureUseCase: ExploreFoodCultureUseCase,
   private val viewer: Viewer
): UiRunner {
    override val id: Int = 10
    override val label: String = "Explore food culture by country"

    override fun run() {
        viewer.display("Enter Country name:")
        readlnOrNull()?.takeIf { it.isNotBlank() }?.let { countryName ->
            try {
                val meals = exploreFoodCultureUseCase.exploreFoodByCountry(country = countryName)
                if (meals.isEmpty()) {
                    viewer.display(" \"$countryName\" is not found in any meal tags.")
                } else {
                    viewer.display("\nFound ${meals.size} meals related to \"$countryName\":")
                    meals.forEachIndexed { index, meal ->
                        viewer.display("${index + 1}. ${meal.name}")
                    }
                }
            } catch (_: Exception) {
                viewer.display("️ Something went wrong while searching for \"$countryName\".")
            }
        } ?: viewer.display(" Please enter a valid country name.")
    }
}