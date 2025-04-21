package data

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class CsvMealsRepository(
    private val csvFileReader: CsvFileReader,
    private val mealsCsvParser: MealsCsvParser
): MealsRepository {

    private val cachedMeals by lazy {
        csvFileReader.readLinesFromFile().map { line ->
            mealsCsvParser.parseColumnsToMeal(line)
        }
    }

    override fun getAllMeals(): List<Meal> = cachedMeals
}