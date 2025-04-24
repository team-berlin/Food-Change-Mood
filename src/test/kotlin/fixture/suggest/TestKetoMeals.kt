package fixture.suggest
import createMeal
import org.berlin.model.Nutrition

object TestKetoMeals {
    val ketoMealValid = createMeal(
        id = 1,
        name = "Avocado Salad",
        nutrition = Nutrition(
            calories = 300.0,
            totalFat = 25.0,
            sugar = 2.0,
            sodium = 150.0,
            proteinGrams = 5.0,
            saturatedFat = 5.0,
            carbohydrates = 8.0  // ≤ 15
        )
    )

    val ketoMealProteinZero = createMeal(
        id = 2,
        name = "Pure Butter",
        nutrition = Nutrition(
            calories = 99.0,
            totalFat = 11.0,
            sugar = 0.0,
            sodium = 82.0,
            proteinGrams = 0.0,
            saturatedFat = 7.0,
            carbohydrates = 0.0
        )
    )

    val nonKetoHighCarb = createMeal(
        id = 3,
        name = "Pasta",
        nutrition = Nutrition(
            calories = 400.0,
            totalFat = 5.0,
            sugar = 3.0,
            sodium = 100.0,
            proteinGrams = 12.0,
            saturatedFat = 1.0,
            carbohydrates = 60.0  // > 15
        )
    )

    val nonKetoLowRatio = createMeal(
        id = 4,
        name = "Lean Chicken",
        nutrition = Nutrition(
            calories = 220.0,
            totalFat = 8.0,
            sugar = 0.0,
            sodium = 120.0,
            proteinGrams = 10.0,  // ratio = (8*9)/(10*4) = 1.8 < 2.0
            saturatedFat = 2.0,
            carbohydrates = 10.0
        )
    )
}
