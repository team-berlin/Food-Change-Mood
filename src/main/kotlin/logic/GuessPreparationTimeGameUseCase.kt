package org.berlin.logic

import org.berlin.model.Meal

class GuessPreparationTimeGameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun guessPreparationTime(): Meal {
        return mealsRepository.getAllMeals()
            .shuffled()
            .first()
        }


}