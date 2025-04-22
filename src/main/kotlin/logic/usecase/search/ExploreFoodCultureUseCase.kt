package org.berlin.logic.usecase.search

import org.berlin.logic.common.extention.getRandomItems
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class ExploreFoodCultureUseCase(
    private val mealsRepository: MealsRepository
) {

    fun exploreFoodByCountry(country: String): List<Meal> =
        mealsRepository
            .getAllMeals()
            .filter { isMealRelatedToCountry(it, country) }
            .takeIf { it.isNotEmpty() }
            ?.getRandomItems(MEALS_NUMBER)
            ?: throw NoSuchElementException("No meals found related to country: $country")

    private fun isMealRelatedToCountry(meal: Meal, country: String): Boolean {
        return meal.tags.any { tag ->
            tag.contains(country, ignoreCase = true) }
                || meal.description?.contains(
            country,
            ignoreCase = true
        ) ?: false
                || meal.name.contains(country, ignoreCase = true)
                || meal.steps.contains(country)
    }

    private companion object {
        const val MEALS_NUMBER = 20
    }
}