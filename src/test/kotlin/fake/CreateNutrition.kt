package fake

import org.berlin.model.Nutrition

fun createNutrition(
        calories : Double = 100.0,
        totalFat : Double = 10.0,
        sugar : Double = 10.0,
        sodium : Double = 10.0,
        proteinGrams : Double = 10.0,
        saturatedFat : Double = 10.0,
        carbohydrates : Double = 10.0
    )
: Nutrition {
    return Nutrition (
        calories = calories ,
        totalFat = totalFat ,
        sugar = sugar ,
        sodium = sodium ,
        proteinGrams = proteinGrams ,
        saturatedFat = saturatedFat ,
        carbohydrates = carbohydrates
    )
}
