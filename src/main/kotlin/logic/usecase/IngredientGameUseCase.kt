package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.logic.IngredientGameMealsMapper
import org.berlin.model.MealForIngredientGame

class IngredientGameUseCase(
    private val repository: MealsRepository,
    private val ingredientGameMealsMapper: IngredientGameMealsMapper
) {

    fun getFifteenMeals(): List<MealForIngredientGame> {

        val meals=repository.getAllMeals()
        if (meals.isEmpty()||meals.size< MAX_QUESTIONS)throw Exception("need more meals list from repo")

        return ingredientGameMealsMapper.map(meals)
            .shuffled()
            .take(MAX_QUESTIONS)


    }

    private companion object Constants {
        const val MAX_QUESTIONS = 15

    }
}