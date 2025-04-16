package org.berlin.data

import data.MealsCsvParser
import data.CsvFileReader
import org.berlin.logic.usecase.EasyFoodSuggestionRepository
import org.berlin.model.Meal

class EasyFoodSuggestionRepositoryImp(
    private val csvFileReader: CsvFileReader,
    private val mealsCsvParser: MealsCsvParser
): EasyFoodSuggestionRepository {
    override fun getEasyFoodSuggestion(): List<Meal> {
       val list = csvFileReader.readLinesFromFile().map { line ->
            mealsCsvParser.parseColumnsToMeal(line)
        }
        val filterList =list.filter { item ->
            item.minutes <= 30 && item.nIngredients <= 5 && item.nSteps <= 6
        }
        val randomList = filterList.shuffled().take(10)
        return randomList
    }

}