package org.berlin.logic.usecase.helper

import org.berlin.logic.common.extention.getRandomItems
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.MealForIngredientGame

class RandomMealsForIngredientGame(
   private val repository: MealsRepository,
   private val ingredientGameMealsMapper: IngredientGameMealsMapper
) {
    private  val mealsForGame  by lazy {  ingredientGameMealsMapper.map(repository.getAllMeals())}

    fun getMeals(numberOfMeals:Int): List<MealForIngredientGame> {
        validateMealsMatchMaxQUESTIONS(numberOfMeals)
        return mealsForGame.getRandomItems(numberOfMeals)
    }
    private fun validateMealsMatchMaxQUESTIONS(numberOfMeals:Int) {
        if (mealsForGame.size < numberOfMeals) throw MealsNotEnoughException("meals to run game is less $numberOfMeals")
    }


}