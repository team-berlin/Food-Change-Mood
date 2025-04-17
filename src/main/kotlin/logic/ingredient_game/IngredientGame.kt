package org.berlin.logic.ingredient_game

import org.berlin.model.GameState
import org.berlin.model.MealForIngredientGame

class IngredientGame(
    private var mealsFormatedForGame:List<MealForIngredientGame>
) {
    private var indexOfMeal=0
    private var score=0
    private var state=GameState.RUNNING

    fun getState()=state
    fun getScore()=score

    fun getMeal():MealForIngredientGame?{
        return if (state!=GameState.RUNNING)null
        else return mealsFormatedForGame[indexOfMeal]
    }

    private fun checkAnswer(mealToCheck: MealForIngredientGame,answer: String):Boolean{
        return mealsFormatedForGame[indexOfMeal].correctIngredient==mealToCheck.correctIngredient
                &&mealToCheck.correctIngredient==answer
    }

    fun updateGameState(mealForIngredientGame: MealForIngredientGame,answer: String){
        if (state!=GameState.RUNNING)return
        if (checkAnswer(mealForIngredientGame,answer)){
            score+=Constants.ANSWER_SCORE
            indexOfMeal++
            if (score==Constants.MAX_SCORE)state=GameState.WON
        }else state=GameState.LOST

    }
     fun reset(){
        indexOfMeal=0
        score=0
        state=GameState.RUNNING
        mealsFormatedForGame=mealsFormatedForGame.shuffled()
    }

    fun isRunning():Boolean{
       return state == GameState.RUNNING
    }

    private object Constants{
       const val MAX_SCORE=15000
        const val ANSWER_SCORE=1000
    }
}