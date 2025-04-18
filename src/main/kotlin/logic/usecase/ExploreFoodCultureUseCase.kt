package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class ExploreFoodCultureUseCase(
    private val mealsRepository: MealsRepository
) {

    fun exploreFoodByCountry(country: String): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { isMealRelatedToCountry(it, country) }
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?.take(MEALS_NUMBER)
            ?: emptyList()
    }

    private fun isMealRelatedToCountry(meal: Meal, country: String): Boolean {
        return  meal.tags.any { tag -> tag.contains(country, ignoreCase = true) } ||
                meal.description?.contains(country, ignoreCase = true) ?: false ||
                meal.name.contains(country, ignoreCase = true) ||
                meal.steps.contains(country)
    }

    private companion object {
        const val MEALS_NUMBER = 20
    }
}
