package org.berlin.logic

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

    fun checkAnswer(answer:String){
        if (state!=GameState.RUNNING)return
        val currentMeal=mealsFormatedForGame[indexOfMeal]

        if (answer==currentMeal.correctIngredient){
            score+=1000
            indexOfMeal++
            if (score==15000)state=GameState.WON
        }else state=GameState.LOST
    }

     fun reset(){
        indexOfMeal=0
        score=0
        state=GameState.RUNNING
        mealsFormatedForGame=mealsFormatedForGame.shuffled()
    }


}