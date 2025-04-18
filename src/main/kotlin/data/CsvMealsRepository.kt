package com.berlin.data

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

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