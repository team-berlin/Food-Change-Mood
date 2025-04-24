package common

import createMeal
import org.berlin.model.Meal
import org.berlin.model.Nutrition

fun genarateEasyMeals(
    count: Int,
    minutes: Int = 30 ,
    numberOfIngredients: Int = 5,
    numberOfStep: Int =4,
    nutrition: Nutrition =
    Nutrition(
        calories =10.0,
        totalFat = 10.0,
        sugar = 10.0,
        sodium = 10.0,
        proteinGrams = 10.0,
        saturatedFat = 10.0,
        carbohydrates = 10.0,
    )
) : List<Meal> {
    return List(count){ index ->
        createMeal(
            id = index,
            name = "Easy Meal",
            minutes = minutes,
            numberOfIngredients = numberOfIngredients,
            numberOfStep = numberOfStep,
            nutrition = nutrition

        )
    }
}


fun generatePotatoMeals(count: Int): List<Meal>{
    return List(count){ index ->
        createMeal(
            id = index,
            name = "Easy Meal",
            ingredients = listOf("potatoes", "potato", "   potatoes  ", "Potato", "Potatoes", "pOtaToeS", "POTATOES", "POTATO")
            )
    }
}