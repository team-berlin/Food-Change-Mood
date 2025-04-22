package logic.usecase.game

import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.IngredientGameMealsMapper
import org.berlin.logic.common.extention.getRandomItems
import org.berlin.model.MealForIngredientGame

class IngredientGameUseCase(
    repository: MealsRepository,
    ingredientGameMealsMapper: IngredientGameMealsMapper
) {

    private val meals = repository.getAllMeals()
    private val mealsForGame = ingredientGameMealsMapper.map(meals)

    fun getFifteenMeals(): List<MealForIngredientGame> {

        validateMealsMatchMaxQUESTIONS()
        return mealsForGame
            .getRandomItems(MAX_QUESTIONS)
    }
    private fun validateMealsMatchMaxQUESTIONS(){
        if (mealsForGame.size < MAX_QUESTIONS)
            throw Exception("meals to run game is less $MAX_QUESTIONS")
    }
    private companion object  {
        const val MAX_QUESTIONS = 15

    }
}