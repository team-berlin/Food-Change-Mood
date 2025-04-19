package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class SearchMealsByNameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun searchMealsByName(inputMealName: String): List<Meal> {
        return mealsRepository.getAllMeals().filter { meal ->
            val mealName = meal.name.lowercase()
            val searchName = inputMealName.lowercase()
            kmpSearchAlgorithm(mealName, searchName)
                    || levenshteinDistanceSearchAlgorithm(mealName, searchName)
        }
    }

    private fun kmpSearchAlgorithm(textToSearchIn: String, wordToSearchBy: String): Boolean {
        if (wordToSearchBy.isEmpty()) return true
        if (textToSearchIn.isEmpty()) return false
        val lps = computeLPSArray(wordToSearchBy)
        var textIndex = 0
        var wordIndex = 0
        while (textIndex < textToSearchIn.length) {
            if (wordToSearchBy[wordIndex] == textToSearchIn[textIndex]) {
                textIndex++
                wordIndex++
            }
            if (wordIndex == wordToSearchBy.length) return true
            else if (textIndex < textToSearchIn.length && wordToSearchBy[wordIndex] != textToSearchIn[textIndex]) {
                if (wordIndex != 0) {
                    wordIndex = lps[wordIndex - 1]

                } else {
                    textIndex++
                }
            }

        }
        return false
    }

    private fun computeLPSArray(wordToSearchBy: String): IntArray {
        val lsp = IntArray(wordToSearchBy.length)
        var prefixLength = 0
        var currentIndex = 1
        while (currentIndex < wordToSearchBy.length) {
            if (wordToSearchBy[currentIndex] == wordToSearchBy[prefixLength]) {
                prefixLength++
                lsp[currentIndex] = prefixLength
                currentIndex++
            } else {
                if (prefixLength != 0) {
                    prefixLength = lsp[prefixLength - 1]
                } else {
                    lsp[currentIndex] = 0
                    currentIndex++
                }
            }
        }
        return lsp
    }

    private fun levenshteinDistanceSearchAlgorithm(textToSearchIn: String, wordToSearchBy: String): Boolean {
        val textToBeSearchLength = textToSearchIn.length
        val wordToSearchByLength = wordToSearchBy.length
        val distanceMatrix = Array(textToBeSearchLength + 1) { IntArray(wordToSearchByLength + 1) }
        (0..textToBeSearchLength).forEach { distanceMatrix[it][0] = it }
        (0..wordToSearchByLength).forEach { distanceMatrix[0][it] = it }
        for (i in 1..textToBeSearchLength) {
            for (j in 1..wordToSearchByLength) {
                val cost = if (textToSearchIn[i - 1] == wordToSearchBy[j - 1]) 0 else 1
                distanceMatrix[i][j] = minOf(
                    distanceMatrix[i - 1][j] + 1,
                    distanceMatrix[i][j - 1] + 1,
                    distanceMatrix[i - 1][j - 1] + cost
                )
            }
        }
        return distanceMatrix[textToBeSearchLength][wordToSearchByLength] <= thresholdForLevenshteinDistance(wordToSearchBy)
    }
    private fun thresholdForLevenshteinDistance(inputMealName: String): Int{
       return (inputMealName.length * 0.3).toInt()

    }

}