package common

import createMeal
import fake.createNutrition
import org.berlin.model.Meal
import org.berlin.model.Nutrition

fun genarateEasyMeals(
    count: Int,
    minutes: Int = 30,
    numberOfIngredients: Int = 5,
    numberOfStep: Int = 4,
    nutrition: Nutrition =
        Nutrition(
            calories = 10.0,
            totalFat = 10.0,
            sugar = 10.0,
            sodium = 10.0,
            proteinGrams = 10.0,
            saturatedFat = 10.0,
            carbohydrates = 10.0,
        )
): List<Meal> {
    return List(count) { index ->
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

fun generatePotatoMeals(count: Int): List<Meal> {
    return List(count) { index ->
        createMeal(
            id = index,
            name = "Easy Meal",
            ingredients = listOf(
                "potatoes",
                "potato",
                "   potatoes  ",
                "Potato",
                "Potatoes",
                "pOtaToeS",
                "POTATOES",
                "POTATO"
            )
        )
    }
}

val seafoodMeals = listOf(
    createMeal(
        name = "tuna salad",
        tags = listOf("seafood"),
        nutrition = createNutrition(proteinGrams = 35.0)
    ),
    createMeal(
        name = "mussels",
        tags = listOf("seafood"),
        nutrition = createNutrition(proteinGrams = 57.0)
    ),
    createMeal(
        name = "salmon pizza",
        tags = listOf("seafood"),
        nutrition = createNutrition(proteinGrams = 68.0)
    )
)

val meals = listOf(
    createMeal(
        name = "tuna salad",
        tags = listOf("seafood"),
        nutrition = createNutrition(proteinGrams = 35.0)
    ),
    createMeal(
        name = "mussels",
        tags = listOf("seafood"),
        nutrition = createNutrition(proteinGrams = 57.0)
    ),
    createMeal(
        name = "chop suey",
        tags = listOf("meat"),
        nutrition = createNutrition(proteinGrams = 51.0)
    ),
    createMeal(
        name = "hollandaise sauce",
        tags = listOf("eggs"),
        nutrition = createNutrition(proteinGrams = 22.0)
    ),
    createMeal(
        name = "salmon pizza",
        tags = listOf("seafood"),
        nutrition = createNutrition(proteinGrams = 68.0)
    )
)