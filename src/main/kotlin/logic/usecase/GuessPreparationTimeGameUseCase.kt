package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class GuessPreparationTimeGameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getRandomMeal(): Meal {
        return mealsRepository.getAllMeals()
            .shuffled()
            .first()
        }
}