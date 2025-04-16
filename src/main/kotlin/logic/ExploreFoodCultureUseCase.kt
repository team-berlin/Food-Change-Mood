package org.berlin.logic

import org.berlin.model.Meal

class ExploreFoodCultureUseCase(
    private val mealsRepository: MealsRepository
) {

    fun exploreFoodByCountry(country: String): List<Meal> {
        val filterMeals = mealsRepository.getAllMeals()
          //  .also { println("Total meals loaded: ${it.size}") }
            .filter { containsCountryInTagsOrDescription(it, country)
            }
            return filterMeals
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?.take(20)
            ?: emptyList()
    }

    private fun containsCountryInTagsOrDescription(meal: Meal, country: String): Boolean {
        return meal.tags.any { it.contains(country, ignoreCase = true) }||
        meal.description?.contains(country, ignoreCase = true) ?: false
    }
}
