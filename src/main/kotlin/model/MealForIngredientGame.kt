package org.berlin.model

data class MealForIngredientGame(
    val mealName:String,
    val correctIngredient:String,
    val threeIngredientOnlyOneCorrect:List<String>
)
