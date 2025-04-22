package org.berlin.logic.usecase.retrieval

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.SeafoodMeal

class GetSeafoodMealsUseCase(private val mealsRepository: MealsRepository) {
    fun getSeafoodMeals(): List<SeafoodMeal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                meal.tags.any { it.contains(TAG_NAME, ignoreCase = true) }
            }
            .sortedByDescending { it.nutrition.protein }
            .map { seafoodMeal ->
                (SeafoodMeal(seafoodMeal.name, seafoodMeal.nutrition.protein))
            }
    }

    companion object {
        const val TAG_NAME = "seafood"
    }
}


