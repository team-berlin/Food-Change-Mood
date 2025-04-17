package org.berlin.logic

import org.berlin.model.Meal

class ExploreFoodCultureUseCase(
    private val mealsRepository: MealsRepository
) {

    fun exploreFoodByCountry(country: String): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { containsCountryInTags(it, country) }
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?.take(MEALS_NUMBER)
            ?: emptyList()
    }

    private fun containsCountryInTags(meal: Meal, country: String): Boolean {
        return meal.tags.any { it
            .trim()
            .split(Regex("\\s+"))
            .filter { it.isNotEmpty() }
            .any { it.contains(country, ignoreCase = true) }
        }
    }

    private companion object {
        const val MEALS_NUMBER = 20
    }
}
