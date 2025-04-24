package common

import createMeal
import org.berlin.model.Meal

fun genarateEasyMeals(count: Int): List<Meal> {
    return List(count){ index ->
        createMeal(
            id = index,
            name = "Easy Meal",
            minutes = index,
            numberOfIngredients = 3,
            numberOfStep = 5,

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