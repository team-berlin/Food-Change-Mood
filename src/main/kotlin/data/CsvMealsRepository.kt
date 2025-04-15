package org.example.data

import org.example.logic.MealsRepository
import org.example.model.Meal

class CsvMealsRepository(
    private val csvFileReader: CsvFileReader,
    private val mealsCsvParser: MealsCsvParser
): MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return csvFileReader.readLinesFromFile().map { line ->
            mealsCsvParser.parseColumnsToMeal(line)
        }
    }
}